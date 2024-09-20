package com.polly.housecowork.utils

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
) {
    data object HouseList : Screen(
        route = Route.HOUSE_LIST
    )

    data class HouseDetail(val houseId: Int) : Screen(
        route = Route.HOUSE_DETAIL
    )

    data object Home : Screen(
        route = Route.HOME
    )

    data object CreateTask : Screen(
        route = Route.ADD_TASK
    )

    data object SignUp : Screen(
        route = Route.SIGN_UP
    )

    data class Profile(val profileId: Int) : Screen(
        route = Route.PROFILE
    )

    data class TaskDetail(val taskId: Int) : Screen(
        route = Route.TASK_DETAIL
    )
}