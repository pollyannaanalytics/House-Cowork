package com.polly.housecowork.compose.house

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.polly.housecowork.utils.Route
import com.polly.housecowork.utils.Step
import com.polly.housecowork.utils.StepState

fun NavGraphBuilder.houseNavigation(
    navController: NavController,
) {
    navigation(
        route = Step.HOUSE_STEP,
        startDestination = StepState.HouseStep.HouseList.step
    ){
        composable(Route.HOUSE_LIST){
            HouseListScreen(
                navigateToJoinHouse = {
                    navController.navigate(Route.JOIN_HOUSE)
                },
                navigateToCreateHouse = {
                    navController.navigate(Route.CREATE_HOUSE)
                }
            )
        }

        composable(Route.JOIN_HOUSE){

        }

        composable(Route.CREATE_HOUSE){
            CreateHouseScreen(
                navigateOnClick = {
                    navController.navigate(Step.HOUSE_STEP)
                }
            )
        }
    }
}