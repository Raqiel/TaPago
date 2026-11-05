package com.example.tapago.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.MarombaButton
import com.example.tapago.ui.components.MarombaSecondButton
import com.example.tapago.ui.components.SmallText
import com.example.tapago.ui.components.TextFieldView
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.ErrorColor
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.NewYellow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navigate: (Destination) -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginEvent.LoggedIn -> navigate(Destination.Home())
                is LoginEvent.ShowMessage ->
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    LoginContent(
        uiState = uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onSubmit = viewModel::submit,
        onToggleMode = viewModel::toggleMode,
        onForgotPassword = viewModel::sendPasswordReset,
        onGoogleClick = { viewModel.signInWithGoogle(context) }
    )
}

@Composable
private fun LoginContent(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onToggleMode: () -> Unit,
    onForgotPassword: () -> Unit,
    onGoogleClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))

        FeaturedText(
            text = stringResource(R.string.login_title),
            size = 34
        )

        Spacer(Modifier.height(40.dp))

        TextFieldView(
            icon = null,
            value = uiState.email,
            placeholder = stringResource(R.string.login_email),
            error = uiState.errorMessage != null,
            onValueChange = onEmailChanged
        )

        Spacer(Modifier.height(16.dp))

        TextFieldView(
            icon = null,
            value = uiState.password,
            placeholder = stringResource(R.string.login_password),
            error = uiState.errorMessage != null,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = onPasswordChanged
        )

        if (uiState.errorMessage != null) {
            Spacer(Modifier.height(12.dp))
            SmallText(
                text = uiState.errorMessage,
                size = 14,
                color = ErrorColor
            )
        }

        Spacer(Modifier.height(32.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(color = FeaturedColor)
        } else {
            MarombaButton(
                textButton = if (uiState.isSignUpMode) {
                    stringResource(R.string.login_create_account)
                } else {
                    stringResource(R.string.login_enter)
                },
                iconButton = Icons.Filled.Check,
                onClick = onSubmit
            )

            Spacer(Modifier.height(12.dp))

            MarombaSecondButton(
                textButton = stringResource(R.string.login_google),
                iconButton = null,
                onClick = onGoogleClick
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = if (uiState.isSignUpMode) {
                "Já tenho conta"
            } else {
                stringResource(R.string.login_create_account)
            },
            color = NewYellow,
            modifier = Modifier.clickable { onToggleMode() }
        )

        if (!uiState.isSignUpMode) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.login_forgot_password),
                color = FeaturedColor,
                modifier = Modifier.clickable { onForgotPassword() }
            )
        }

        Spacer(Modifier.height(40.dp))
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginContent(
        uiState = LoginUiState(email = "frango@academia.com", password = "123456"),
        onEmailChanged = {},
        onPasswordChanged = {},
        onSubmit = {},
        onToggleMode = {},
        onForgotPassword = {},
        onGoogleClick = {}
    )
}
