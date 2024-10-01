package com.polly.housecowork.compose.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.polly.housecowork.compose.onboarding.signup.SignUpScreen
import com.polly.housecowork.utils.Route
import com.polly.housecowork.utils.Step
import com.polly.housecowork.utils.StepState

fun NavGraphBuilder.onboardingNavigation(
    navController: NavController,
    onOnboardingComplete: () -> Unit
){
    navigation(
        route = Step.ONBOARDING_STEP,
        startDestination = Route.SIGN_UP
    ){
        composable(Route.SIGN_UP){
            SignUpScreen(
                navigateToHome = {
                    onOnboardingComplete()
                }
            )
        }

    }
}