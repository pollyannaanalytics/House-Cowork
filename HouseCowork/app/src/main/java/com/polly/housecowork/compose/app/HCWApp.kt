package com.polly.housecowork.compose.app

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.compose.createtask.CreateTaskScreen
import com.polly.housecowork.compose.home.HomeScreen
import com.polly.housecowork.compose.profile.ProfileScreen
import com.polly.housecowork.compose.signup.SignUpScreen
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.viewmodel.HouseCoworkAppViewModel


@Composable
fun HCWApp(
    viewModel: HouseCoworkAppViewModel= hiltViewModel()) {
    val navController = rememberNavController()
    val profileInfoState by viewModel.profileInfo.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            profileInfoState?.let {
                HCWAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .background(LocalColorScheme.current.background)
                        .padding(16.dp),
                    profileInfo = it,
                    navigateToProfile = { userProfile ->
                        navController.navigate(
                            Screen.Profile(profileId = userProfile.id).route
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        HCWNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            profileId = { profileInfoState?.id ?: 0 },
            taskId = { 0 }
        )
    }
}


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
    }
}