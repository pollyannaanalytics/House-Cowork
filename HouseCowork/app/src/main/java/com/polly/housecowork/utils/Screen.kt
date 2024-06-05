package com.polly.housecowork.utils

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
    ){
    data object Home: Screen(Route.HOME)
}