package com.polly.housecowork.compose.onboarding.register

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.compose.onboarding.OnboardingStep
import com.polly.housecowork.compose.splash.SplashScreen
import com.polly.housecowork.utils.Screen

@Composable
fun RegisterScreen(
    navController: NavHostController = rememberNavController(),
    onRegisterComplete: () -> Unit,
    ) {
    NavHost(
        navController = navController,
        startDestination = OnboardingStep.Register.SignUp.step
    ){
        composable(OnboardingStep.Register.SignUp.step){
            SignUpScreen(
                onFinishSignUp = {
                    navController.navigate(OnboardingStep.Register.Splash.step)
                }
            )
        }

        composable(OnboardingStep.Register.Splash.step){
            SplashScreen(
                onNavigateHome = onRegisterComplete
            )
        }
    }
}