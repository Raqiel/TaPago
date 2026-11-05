package com.example.tapago.data.remote.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.example.tapago.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?
)

sealed class AuthError(val message: String) {
    data object InvalidCredentials : AuthError("E-mail ou senha incorretos")
    data object EmailAlreadyInUse : AuthError("Esse e-mail já tem conta")
    data object WeakPassword : AuthError("A senha precisa de pelo menos 6 caracteres")
    data object UserNotFound : AuthError("Não encontrei conta com esse e-mail")
    data object Cancelled : AuthError("Login cancelado")
    data object NoGoogleAccount : AuthError("Nenhuma conta Google disponível neste aparelho")
    data class Unknown(val detail: String) : AuthError(detail)
}

class AuthException(val error: AuthError) : Exception(error.message)

interface AuthRepository {
    val currentUserId: String?

    fun observeAuthState(): Flow<AuthUser?>

    suspend fun signInWithEmail(email: String, password: String): Result<AuthUser>
    suspend fun signUpWithEmail(email: String, password: String): Result<AuthUser>

    suspend fun signInWithGoogle(activityContext: Context): Result<AuthUser>

    suspend fun sendPasswordReset(email: String): Result<Unit>
    fun signOut()
}

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val appContext: Context
) : AuthRepository {

    override val currentUserId: String?
        get() = auth.currentUser?.uid

    override fun observeAuthState(): Flow<AuthUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.toAuthUser())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<AuthUser> =
        runCatching {
            val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
            result.user?.toAuthUser() ?: throw AuthException(AuthError.Unknown("Login sem usuário"))
        }.mapFirebaseError()

    override suspend fun signUpWithEmail(email: String, password: String): Result<AuthUser> =
        runCatching {
            val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
            result.user?.toAuthUser() ?: throw AuthException(AuthError.Unknown("Cadastro sem usuário"))
        }.mapFirebaseError()

    override suspend fun signInWithGoogle(activityContext: Context): Result<AuthUser> =
        runCatching {
            val credentialManager = CredentialManager.create(activityContext)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(appContext.getString(R.string.web_client_id))
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = credentialManager.getCredential(activityContext, request)
            val credential = response.credential

            if (credential !is CustomCredential ||
                credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                throw AuthException(AuthError.Unknown("Credencial do Google inesperada"))
            }

            val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val result = auth.signInWithCredential(firebaseCredential).await()

            result.user?.toAuthUser()
                ?: throw AuthException(AuthError.Unknown("Login com Google sem usuário"))
        }.mapFirebaseError()

    override suspend fun sendPasswordReset(email: String): Result<Unit> =
        runCatching {
            auth.sendPasswordResetEmail(email.trim()).await()
            Unit
        }.mapFirebaseError()

    override fun signOut() {
        auth.signOut()
    }
}

private fun com.google.firebase.auth.FirebaseUser.toAuthUser() = AuthUser(
    uid = uid,
    email = email,
    displayName = displayName,
    photoUrl = photoUrl?.toString()
)

private fun <T> Result<T>.mapFirebaseError(): Result<T> = recoverCatching { throwable ->
    val error = when (throwable) {
        is AuthException -> throwable.error
        is FirebaseAuthWeakPasswordException -> AuthError.WeakPassword
        is FirebaseAuthUserCollisionException -> AuthError.EmailAlreadyInUse
        is FirebaseAuthInvalidUserException -> AuthError.UserNotFound
        is FirebaseAuthInvalidCredentialsException -> AuthError.InvalidCredentials
        is GetCredentialCancellationException -> AuthError.Cancelled
        is NoCredentialException -> AuthError.NoGoogleAccount
        is GetCredentialException -> AuthError.Unknown(
            throwable.message ?: "Falha no login com Google"
        )
        else -> AuthError.Unknown(throwable.message ?: "Erro desconhecido")
    }
    throw AuthException(error)
}
