package com.polly.housecowork.compose.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, joinOnClick: () -> Unit = {}){
    Column(modifier.padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Welcome(modifier = Modifier.padding(16.dp))
        SignUpTitle(modifier = Modifier.padding(16.dp))
        SignUpForm(
            nameError = { false},
            emailError = { false },
            passwordError = { false },
            repeatPasswordError = {false}
            )
    }
}

@Composable
fun Welcome(modifier: Modifier = Modifier){
    Row(modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
        Text(text = "Let's",
            style = LocalTypography.current.bodyLarge
            )
        Text(text = " House Cowork :)",
            style = LocalTypography.current.headlineSmall
            )
    }
}
@Composable
fun SignUpTitle(modifier: Modifier = Modifier){
    Text(text = "Sign Up",
        style = LocalTypography.current.headlineLarge,
        color = LocalColorScheme.current.onPrimary
        )

}

@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}