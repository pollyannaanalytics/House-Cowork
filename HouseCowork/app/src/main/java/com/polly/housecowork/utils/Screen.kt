package com.polly.housecowork.utils

import androidx.navigation.NamedNavArgument
import com.polly.housecowork.utils.Step.Companion.ONBOARDING_STEP

sealed class StepState(val step: String){
    data object Onboarding : StepState(step = ONBOARDING_STEP)
    data object Splash : StepState(step = Step.SPLASH_STEP)
    data object Home : StepState(step = Step.HOME_STEP)
    data object Profile : StepState(step = Step.PROFILE_STEP)
    data object CreateTask : StepState(step = Step.CREATE_TASK_STEP)
    data object Chat: StepState(step = Route.CHAT)
    data object Money: StepState(step = Route.MONEY)
    data class TaskDetail(val taskId: Int) : StepState(step = Route.TASK_DETAIL)

    sealed class HouseStep: StepState(Step.HOUSE_STEP){
        data object HouseList : StepState(step = Route.HOUSE_LIST)
        data class HouseDetail(val houseId: Int) : StepState(step = Route.HOUSE_DETAIL)
        data object CreateHouse : StepState(step = Route.CREATE_HOUSE)
        data object JoinHouse : StepState(step = Route.JOIN_HOUSE)
    }


}

sealed class Screen(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
) {
    data object HouseList : Screen(
        route = Route.HOUSE_LIST
    )

    data object CreateHouse : Screen(
        route = Route.CREATE_HOUSE
    )

    data object JoinHouse : Screen(
        route = Route.JOIN_HOUSE
    )

    data class HouseDetail(val houseId: Int) : Screen(
        route = Route.HOUSE_DETAIL
    )

    data object Home : Screen(
        route = Route.HOME
    )

    data object CreateTask : Screen(
        route = Route.CREATE_TASK
    )

    data object SignUp : Screen(
        route = Route.SIGN_UP
    )

    data object Splash : Screen(
        route = Route.SPLASH
    )

    data class Profile(val profileId: Int) : Screen(
        route = Route.PROFILE
    )

    data class TaskDetail(val taskId: Int) : Screen(
        route = Route.TASK_DETAIL
    )
}