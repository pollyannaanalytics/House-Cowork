package com.polly.housecowork.compose.app

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.polly.housecowork.compose.createtask.CreateTaskScreen
import com.polly.housecowork.compose.home.HomeScreen
import com.polly.housecowork.compose.house.HouseListScreen
import com.polly.housecowork.compose.profile.ProfileScreen
import com.polly.housecowork.compose.signup.SignUpScreen
import com.polly.housecowork.utils.Screen


@Composable
fun HCWNavHost(
    modifier: Modifier,
    navController: NavHostController,
    profileId: () -> Int,
    taskId: () -> Int
) {
    val activity = (LocalContext.current as Activity)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.SignUp.route
    ) {
        composable(Screen.SignUp.route) {
            SignUpScreen(
                modifier = Modifier.fillMaxSize(),
                navigateToHome = {
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
        composable(Screen.CreateTask.route) {
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
                profileId = profileId()
            )
        }

        composable(Screen.HouseList.route) {
            HouseListScreen(
                modifier = Modifier.fillMaxSize(),
                navigateToHouseDetail = { house ->
                    navController.navigate(Screen.HouseDetail(houseId = house.id).route)
                }
            )
        }
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