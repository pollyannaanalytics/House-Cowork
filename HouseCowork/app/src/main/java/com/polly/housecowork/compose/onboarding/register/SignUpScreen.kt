package com.polly.housecowork.compose.onboarding.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    onFinishSignUp: () -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    val errorState by viewModel.errorState.collectAsState()
    Column(
        modifier
            .clickable { focusManager.clearFocus() }
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Welcome(modifier = Modifier.padding(16.dp).clickable { onFinishSignUp() })
        SignUpTitle(modifier = Modifier.padding(8.dp))
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
                    onFinishSignUp()
                }
            }
        )
    }
}

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            text = "Let's",
            style = LocalTypography.current.bodyMedium
        )
        Text(
            text = " House Cowork :)",
            style = LocalTypography.current.headlineMedium
        )
    }
}

@Composable
fun SignUpTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
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