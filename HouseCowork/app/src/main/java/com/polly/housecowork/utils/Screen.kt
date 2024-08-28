package com.polly.housecowork.utils

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val pageTitle: String,
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen(
        pageTitle = "",
        route = Route.HOME
    )

    data object CreateTask : Screen(
        pageTitle = "Create a Task",
        route = Route.ADD_TASK
    )

    data object SignUp : Screen(
        pageTitle = "Sign Up",
        route = Route.SIGN_UP
    )

    data class Profile(val profileId: Int) : Screen(
        pageTitle = "Profile",
        route = Route.PROFILE
    )

    data class TaskDetail(val taskId: Int) : Screen(
        pageTitle = "Task Detail",
        route = Route.TASK_DETAIL
    )




}