package com.polly.housecowork.compose

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.compose.home.HomeScreen
import com.polly.housecowork.utils.Route
import com.polly.housecowork.utils.Screen


@Composable
fun HouseCoworkApp() {
    val navController = rememberNavController()
    HouseCoworkNavHost(navController = navController)

}

@Composable
fun HouseCoworkNavHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
             HomeScreen(
                 modifier = Modifier.fillMaxSize(),
             )
        }
    }


}