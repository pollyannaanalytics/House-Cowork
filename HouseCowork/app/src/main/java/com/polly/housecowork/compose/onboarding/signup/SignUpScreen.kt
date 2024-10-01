package com.polly.housecowork.compose.onboarding.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    val errorState by viewModel.errorState.collectAsState()
    Column(
        modifier
            .clickable { focusManager.clearFocus() }
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Welcome(modifier = Modifier.padding(16.dp))
        SignUpTitle(modifier = Modifier.padding(16.dp))
        SignUpForm(
            nameOnChange = { viewModel.setUsername(it) },
            emailOnChange = { viewModel.setEmail(it) },
            passwordOnChange = { viewModel.setPassword(it) },
            repeatPasswordOnChange = { viewModel.confirmSamePassword(it) },
            emailError = errorState.errorEmail,
            passwordError = errorState.errorPassword,
            repeatPasswordError = errorState.errorRepeatedPassword,
            joinOnClick = {
                if (viewModel.checkAllFieldsValid()) {
                    viewModel.setUpUserInfo()
                    navigateToHome()
                }
            }
        )
    }
}

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = "Let's",
            style = LocalTypography.current.bodyLarge
        )
        Text(
            text = " House Cowork :)",
            style = LocalTypography.current.headlineSmall
        )
    }
}

@Composable
fun SignUpTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Sign Up",
        style = LocalTypography.current.headlineLarge,
        color = LocalColorScheme.current.onPrimary
    )

}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}