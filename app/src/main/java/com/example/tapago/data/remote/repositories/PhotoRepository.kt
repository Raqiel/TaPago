package com.example.tapago.data.remote.repositories

import android.content.Context
import android.net.Uri
import com.example.tapago.data.images.compressImage
import com.example.tapago.data.remote.auth.AuthRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface PhotoRepository {
    suspend fun uploadWorkoutPhoto(workoutId: String, imageUri: Uri): Result<String>
    suspend fun uploadExercisePhoto(exerciseId: String, imageUri: Uri): Result<String>
    suspend fun deleteWorkoutPhoto(downloadUrl: String): Result<Unit>
}

class PhotoRepositoryImpl(
    private val storage: FirebaseStorage,
    private val authRepository: AuthRepository,
    private val appContext: Context
) : PhotoRepository {

    override suspend fun uploadWorkoutPhoto(
        workoutId: String,
        imageUri: Uri
    ): Result<String> = uploadPhoto("workouts/$workoutId.jpg", imageUri)

    override suspend fun uploadExercisePhoto(
        exerciseId: String,
        imageUri: Uri
    ): Result<String> = uploadPhoto("exercises/$exerciseId.jpg", imageUri)

    private suspend fun uploadPhoto(
        path: String,
        imageUri: Uri
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val uid = authRepository.currentUserId ?: throw NotLoggedInException()
            val bytes = compressImage(appContext, imageUri)

            val reference = storage.reference.child("users/$uid/$path")
            reference.putBytes(bytes).await()
            reference.downloadUrl.await().toString()
        }
    }

    override suspend fun deleteWorkoutPhoto(downloadUrl: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                storage.getReferenceFromUrl(downloadUrl).delete().await()
                Unit
            }
        }
}
