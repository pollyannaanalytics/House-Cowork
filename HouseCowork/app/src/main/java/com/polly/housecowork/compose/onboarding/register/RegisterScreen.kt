package com.polly.housecowork.compose.onboarding.register

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.compose.onboarding.OnboardingStep

@Composable
fun RegisterScreen(
    navController: NavHostController = rememberNavController(),
    onSignUpComplete: () -> Unit,
    ) {
    NavHost(
        navController = navController,
        startDestination = OnboardingStep.Register.SignUp.step
    ){
        composable(OnboardingStep.Register.SignUp.step){
            SignUpScreen(
                onSignUpComplete = onSignUpComplete
            )
        }
    }
}