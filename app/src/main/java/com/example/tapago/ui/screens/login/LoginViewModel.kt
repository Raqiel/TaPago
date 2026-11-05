package com.example.tapago.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapago.data.remote.auth.AuthException
import com.example.tapago.data.remote.auth.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSignUpMode: Boolean = false,
    val errorMessage: String? = null
) {
    val canSubmit: Boolean
        get() = !isLoading && email.isNotBlank() && password.length >= 6
}

sealed interface LoginEvent {
    data object LoggedIn : LoginEvent
    data class ShowMessage(val message: String) : LoginEvent
}

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email = value, errorMessage = null)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value, errorMessage = null)
    }

    fun toggleMode() {
        _uiState.value = _uiState.value.copy(
            isSignUpMode = !_uiState.value.isSignUpMode,
            errorMessage = null
        )
    }

    fun submit() {
        val state = _uiState.value
        if (!state.canSubmit) return

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMessage = null)

            val result = if (state.isSignUpMode) {
                authRepository.signUpWithEmail(state.email, state.password)
            } else {
                authRepository.signInWithEmail(state.email, state.password)
            }

            handleResult(result.map { })
        }
    }

    fun signInWithGoogle(activityContext: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            handleResult(authRepository.signInWithGoogle(activityContext).map { })
        }
    }

    fun sendPasswordReset() {
        val email = _uiState.value.email
        if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Digite seu e-mail primeiro")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            authRepository.sendPasswordReset(email)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(LoginEvent.ShowMessage("Enviei um e-mail pra você redefinir a senha"))
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = throwable.toUiMessage()
                    )
                }
        }
    }

    private suspend fun handleResult(result: Result<Unit>) {
        result
            .onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _eventFlow.emit(LoginEvent.LoggedIn)
            }
            .onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.toUiMessage()
                )
            }
    }

    private fun Throwable.toUiMessage(): String = when (this) {
        is AuthException -> error.message
        else -> message ?: "Erro desconhecido"
    }
}
