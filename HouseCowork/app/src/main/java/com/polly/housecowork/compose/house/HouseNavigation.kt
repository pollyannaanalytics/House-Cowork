package com.polly.housecowork.compose.house

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.polly.housecowork.utils.Screen

fun NavGraphBuilder.houseNavigation(
    navController: NavController,
    onCompleted: () -> Unit
) {
    navigation(
        route = Screen.House.BASE_ROUTE,
        startDestination = Screen.House.Base.route
    ){
        composable(Screen.House.Base.route){
            HouseBaseScreen(
                navigateToJoinHouse = {
                    navController.navigate(Screen.House.Join.route)
                },
                navigateToCreateHouse = {
                    navController.navigate(Screen.House.Create.route)
                }
            )
        }

        composable(Screen.House.Join.route){

        }

        composable(Screen.House.Create.route){
            CreateHouseScreen(
                navigateOnClick = {
                    navController.popBackStack()
                },
                onCompleteClick = {
                    onCompleted()
                }
            )
        }
    }
}