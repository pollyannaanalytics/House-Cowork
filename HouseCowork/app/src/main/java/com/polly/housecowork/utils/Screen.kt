package com.polly.housecowork.utils

import androidx.navigation.NamedNavArgument
import com.polly.housecowork.utils.Route.Companion.HOUSE_DETAIL_ROUTE
import com.polly.housecowork.utils.Route.Companion.TASK_DETAIL_ROUTE

sealed class Screen(
    val title: String,
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
) {
    sealed class OnBoarding(title: String, route: String) : Screen(title, route) {
        data object SignUp : OnBoarding(ScreenTitle.SIGN_UP,"$BASE_ROUTE/${Route.SIGN_UP_ROUTE}")
        data object Login : OnBoarding(ScreenTitle.LOGIN,"$BASE_ROUTE/${Route.LOGIN_ROUTE}")
//        data object CompleteProfile : OnBoarding(ScreenTitle.COMPLETE_PROFILE,"$BASE_ROUTE/${Route.COMPLETE_PROFILE_ROUTE}")
        companion object{
            const val BASE_ROUTE = "onboarding"
        }
    }

    sealed class House(title: String, route: String) : Screen(title, route) {
        data object Base : House(ScreenTitle.HOUSE, "$BASE_ROUTE/${Route.HOUSE_LIST_ROUTE}")
        data class Detail(val houseId: Int) : House(ScreenTitle.HOUSE_DETAIL, "$BASE_ROUTE/${Route.HOUSE_DETAIL_ROUTE}")
        data object Create : House(ScreenTitle.CREATE_HOUSE, "$BASE_ROUTE/${Route.CREATE_HOUSE_ROUTE}")
        data object Join : House(ScreenTitle.JOIN_HOUSE,"$BASE_ROUTE/${Route.JOIN_HOUSE_ROUTE}")

        companion object{
            const val BASE_ROUTE = "house"
        }
    }

    sealed class Task(title: String, route: String) : Screen(title, route) {
        data object Create : Task(ScreenTitle.CREATE_TASK, "$BASE_ROUTE/${Route.CREATE_TASK_ROUTE}")
        data class Detail(val taskId: Int) : Task(ScreenTitle.TASK_DETAIL,"${House.BASE_ROUTE}/${Route.TASK_DETAIL_ROUTE}/${taskId}")
        companion object{
            const val BASE_ROUTE = "task"
        }
    }

    data object Splash : Screen(ScreenTitle.SPLASH, Route.SPLASH_ROUTE)
    data object Home : Screen(ScreenTitle.HOME, Route.HOME_ROUTE)
    data object Profile : Screen(ScreenTitle.PROFILE, Route.PROFILE_ROUTE)
    data object Chat : Screen(ScreenTitle.CHAT, Route.CHAT_ROUTE)
    data object Money : Screen(ScreenTitle.MONEY, Route.MONEY_ROUTE)

companion object {
    private val screens = listOf(
        Home,
        OnBoarding.SignUp,
//        OnBoarding.CompleteProfile,
        House.Base,
        House.Create,
        House.Join,
        Task.Create,
        Splash
    )

    private val screenPatterns = mapOf(
        HOUSE_DETAIL_ROUTE to { r: String ->
            val id = r.substringAfterLast("/").toIntOrNull() ?: -1
            House.Detail(id)
        },
        TASK_DETAIL_ROUTE to { r: String ->
            val id = r.substringAfterLast("/").toIntOrNull() ?: -1
            Task.Detail(id)
        }
    )

    fun fromRoute(route: String?): Screen {
        if (route == null) return Home

        screens.firstOrNull { it.route == route }?.let { return it }

        screenPatterns.entries
            .firstOrNull { (pattern, _) -> route.startsWith(pattern) }
            ?.value
            ?.invoke(route)
            ?.let { return it }

        return Home
    }
}

}