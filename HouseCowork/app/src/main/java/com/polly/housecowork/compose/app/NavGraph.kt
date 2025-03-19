package com.polly.housecowork.compose.app

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

@Composable
fun HCWNavHost(
    modifier: Modifier,
    navController: NavHostController,
    appState: HCWAppState
) {
    val graphStartDestination = appState.startDestination

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = graphStartDestination
    ) {
        onboardingNavigation(
            navController = navController,
            startDestination = appState.userNextRoute,
            onOnboardingComplete = {
                navController.navigate(Screen.House.BASE_ROUTE)
            }
        )
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                navigateTo = { screen ->
                    navController.navigate(screen.route)
                }
            )
        }

        composable(Screen.Task.Create.route) {
            CreateTaskScreen(
                modifier = Modifier.fillMaxSize(),
                navigateOnClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(Screen.Profile.title) {
            ProfileScreen(
                modifier = Modifier.fillMaxSize(),
                navigateOnClick = {
                    navController.popBackStack()
                },
            )
        }

        houseNavigation(
            navController = navController,
            onCompleted = {
                navController.navigate(Screen.Home.route)
            }
        )
    }
}
