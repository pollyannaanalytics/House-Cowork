package com.polly.housecowork.compose.house

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.polly.housecowork.utils.ScreenTitle
import com.polly.housecowork.utils.Screen

fun NavGraphBuilder.houseNavigation(
    navController: NavController,
) {
    navigation(
        route = Screen.House.BASE_ROUTE,
        startDestination = Screen.House.List.route
    ){
        composable(Screen.House.List.route){
            HouseListScreen(
                navigateToJoinHouse = {
                    navController.navigate(ScreenTitle.JOIN_HOUSE)
                },
                navigateToCreateHouse = {
                    navController.navigate(ScreenTitle.CREATE_HOUSE)
                }
            )
        }

        composable(Screen.House.Join.route){

        }

        composable(Screen.House.Create.route){
            CreateHouseScreen(
                navigateOnClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}