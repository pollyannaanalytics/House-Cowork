package com.polly.housecowork.compose.house

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.polly.housecowork.utils.Route
import com.polly.housecowork.utils.Screen

fun NavGraphBuilder.houseNavigation(
    navController: NavController,
    onCompleted: (Int) -> Unit
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
                onCompleteClick = { houseId ->
                    onCompleted(houseId)
                }
            )
        }

        composable(
            route = "${Screen.House.BASE_ROUTE}/${Route.HOUSE_DETAIL_ROUTE}/{houseId}",
            arguments = listOf(navArgument("houseId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val houseId = navBackStackEntry.arguments?.getInt("houseId") ?: -1
            HouseDetailScreen(houseId = houseId)
        }
    }
}