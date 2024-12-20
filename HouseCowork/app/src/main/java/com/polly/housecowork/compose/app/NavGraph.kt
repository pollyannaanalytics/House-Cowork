package com.polly.housecowork.compose.app

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.polly.housecowork.compose.createtask.CreateTaskScreen
import com.polly.housecowork.compose.home.HomeScreen
import com.polly.housecowork.compose.house.houseNavigation
import com.polly.housecowork.compose.onboarding.onboardingNavigation
import com.polly.housecowork.compose.profile.ProfileScreen
import com.polly.housecowork.compose.splash.SplashScreen
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.utils.StepState

@Composable
fun HCWNavHost(
    modifier: Modifier,
    navController: NavHostController,
    appState: HCWAppState
) {
    val graphStartDestination = appState.startDestination
    Log.d("HCWNavHost", "graphStartDestination: ${graphStartDestination.step}")
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = graphStartDestination.step
    ) {
        onboardingNavigation(
            authState = appState.authState,
            onOnboardingComplete = {
                navController.navigate(StepState.Splash.step)
            }
        )
        composable(StepState.Splash.step){
            SplashScreen(
                onNavigateHome = {
                    navController.navigate(StepState.Home.step)
                }
            )
        }
        composable(StepState.Home.step) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                navigateTo = { stepStep ->
                    navController.navigate(stepStep.step)
                }
            )
        }

        composable(StepState.CreateTask.step) {
            CreateTaskScreen(
                modifier = Modifier.fillMaxSize(),
                navigateOnClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(StepState.CreateTask.step) {
            CreateTaskScreen(
                modifier = Modifier.fillMaxSize(),
                navigateOnClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile(profileId = appState.profileInfo?.id ?: 0).route) {
            ProfileScreen(
                modifier = Modifier.fillMaxSize(),
                navigateOnClick = {
                    navController.popBackStack()
                },
            )
        }
        houseNavigation(navController = navController)
    }
}
