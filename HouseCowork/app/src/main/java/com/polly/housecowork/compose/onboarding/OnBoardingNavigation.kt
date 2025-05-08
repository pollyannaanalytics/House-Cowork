package com.polly.housecowork.compose.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.polly.housecowork.compose.house.houseNavigation
import com.polly.housecowork.compose.onboarding.register.SignUpScreen
import com.polly.housecowork.utils.Screen

fun NavGraphBuilder.onboardingNavigation(
    navController: NavController,
    startDestination: String,
    onOnboardingComplete: (Int) -> Unit
) {
    // todo: should be login page when page is designed
    navigation(
        route = Screen.OnBoarding.BASE_ROUTE,
        startDestination = startDestination
    ) {
        composable(Screen.OnBoarding.SignUp.route) {
            SignUpScreen(
                onSignUpComplete = {
                    navController.navigate(Screen.House.BASE_ROUTE)
                }
            )
        }

        composable(Screen.OnBoarding.Login.route) {
            LoginScreen()
        }

        houseNavigation(navController, onOnboardingComplete)
    }
}