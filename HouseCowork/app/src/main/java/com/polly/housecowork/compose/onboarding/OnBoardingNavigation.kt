package com.polly.housecowork.compose.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.compose.onboarding.register.RegisterScreen

import com.polly.housecowork.model.auth.AuthState
import com.polly.housecowork.utils.Step
import com.polly.housecowork.utils.Step.Companion.ONBOARDING_STEP


sealed class OnboardingStep {
    sealed class Register(val step: String) : OnboardingStep() {
        data object SignUp : Register(step = Step.SIGN_UP_STEP)
    }

}

fun NavGraphBuilder.onboardingNavigation(
    authState: AuthState,
    onOnboardingComplete: () -> Unit
) {
    navigation(
        route = ONBOARDING_STEP,
        startDestination = Step.REGISTER_STEP
    ) {
        composable(Step.REGISTER_STEP) {
            RegisterScreen(
                onSignUpComplete = onOnboardingComplete
            )
        }
    }
}