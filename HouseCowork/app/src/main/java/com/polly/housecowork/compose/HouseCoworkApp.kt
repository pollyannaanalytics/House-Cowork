package com.polly.housecowork.compose

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.compose.createtask.CreateTaskScreen
import com.polly.housecowork.compose.home.HomeScreen
import com.polly.housecowork.compose.signup.SignUpScreen
import com.polly.housecowork.utils.Screen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HouseCoworkApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        HouseCoworkNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HouseCoworkNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.SignUp.route
    ) {
        composable(Screen.SignUp.route){
            SignUpScreen(
                modifier = Modifier.fillMaxSize(),
                joinOnClick = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onTaskClick = {
                    navController.navigate(Screen.CreateTask.route)
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
    }
}