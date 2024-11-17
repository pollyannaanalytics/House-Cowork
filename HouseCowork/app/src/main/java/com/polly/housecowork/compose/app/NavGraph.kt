package com.polly.housecowork.compose.app

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.polly.housecowork.compose.createtask.CreateTaskScreen
import com.polly.housecowork.compose.home.HomeScreen
import com.polly.housecowork.compose.house.houseNavigation
import com.polly.housecowork.compose.onboarding.onboardingNavigation
import com.polly.housecowork.compose.profile.ProfileScreen
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.utils.StepState

@Composable
fun HCWNavHost(
    modifier: Modifier,
    navController: NavHostController,
    graphStartDestination: StepState,
    profileId: () -> Int,
    taskId: () -> Int
) {
    val activity = (LocalContext.current as Activity)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = graphStartDestination.step
    ) {
        onboardingNavigation(
            navController = navController,
            onOnboardingComplete = {
                navController.navigate(StepState.Home.step)
            }
        )
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

        composable(StepState.TaskDetail(taskId = taskId()).step) {
            CreateTaskScreen(
                modifier = Modifier.fillMaxSize(),
                navigateOnClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile(profileId = profileId()).route) {
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



class MainActions(navController: NavHostController) {
    val navigateTo: (Screen) -> Unit = { screen ->
        navController.navigate(screen.route)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}