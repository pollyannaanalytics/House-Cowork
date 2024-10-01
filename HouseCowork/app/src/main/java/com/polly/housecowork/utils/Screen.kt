package com.polly.housecowork.utils

import androidx.navigation.NamedNavArgument
import com.polly.housecowork.utils.Step.Companion.ONBOARDING_STEP

sealed class StepState(val step: String){
    data object Onboarding : StepState(step = ONBOARDING_STEP)
    data object Home : StepState(step = Step.HOME_STEP)
    data object Profile : StepState(step = Step.PROFILE_STEP)
    data object CreateTask : StepState(step = Route.ADD_TASK)
    data object TaskDetail : StepState(step = Route.TASK_DETAIL)

    sealed class HouseStep(route: String): StepState(Step.HOUSE_STEP){
        data object HouseList : HouseStep(Route.HOUSE_LIST)
        data object HouseDetail : HouseStep(Route.HOUSE_DETAIL)
        data object CreateHouse : HouseStep(Route.CREATE_HOUSE)
        data object JoinHouse : HouseStep(Route.JOIN_HOUSE)
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