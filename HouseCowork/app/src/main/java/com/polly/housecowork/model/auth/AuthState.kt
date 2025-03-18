package com.polly.housecowork.model.auth

import com.polly.housecowork.utils.Screen

sealed class OnboardingState {
    sealed class Onboarding(val prefName: String): OnboardingState() {
        data object SignUp : Onboarding(PREF_KEY_SIGN_UP)
        data object Login : Onboarding(PREF_KEY_LOGIN)
        data object CompleteProfile : Onboarding(PREF_KEY_COMPLETE_PROFILE)
        data object CreateHouse : Onboarding(PREF_KEY_CREATE_HOUSE)
    }

    sealed class Auth : OnboardingState() {
        data object Complete : Auth()
        data object Incomplete : Auth()
    }


    companion object {
        const val PREF_KEY_SIGN_UP = "sign_up"
        const val PREF_KEY_LOGIN = "login"
        const val PREF_KEY_COMPLETE_PROFILE = "complete_profile"
        const val PREF_KEY_CREATE_HOUSE = "create_house"
        const val PREF_KEY_COMPLETE = "complete"
        const val PREF_KEY_INCOMPLETE = "incomplete"

        fun String.toUserState(): OnboardingState {
            return when (this) {
                PREF_KEY_SIGN_UP -> Onboarding.SignUp
                PREF_KEY_LOGIN -> Onboarding.Login
                PREF_KEY_COMPLETE_PROFILE -> Onboarding.CompleteProfile
                PREF_KEY_CREATE_HOUSE -> Onboarding.CreateHouse
                PREF_KEY_COMPLETE -> Auth.Complete
                PREF_KEY_INCOMPLETE -> Auth.Incomplete
                else -> Auth.Incomplete
            }
        }

        fun OnboardingState.toPrefKey(): String {
            return when (this) {
                is Onboarding.SignUp -> PREF_KEY_SIGN_UP
                is Onboarding.Login -> PREF_KEY_LOGIN
                is Onboarding.CompleteProfile -> PREF_KEY_COMPLETE_PROFILE
                is Onboarding.CreateHouse -> PREF_KEY_CREATE_HOUSE
                is Auth.Complete -> PREF_KEY_COMPLETE
                Auth.Incomplete -> PREF_KEY_INCOMPLETE
            }
        }

        fun OnboardingState.toRoute(): String {
            return when (this) {
                is Onboarding.SignUp -> Screen.OnBoarding.SignUp.route
                is Onboarding.Login -> Screen.OnBoarding.Login.route
                is Onboarding.CompleteProfile -> Screen.OnBoarding.CompleteProfile.route
                is Onboarding.CreateHouse -> Screen.House.BASE_ROUTE
                is Auth.Complete -> Screen.Home.route
                Auth.Incomplete -> Screen.OnBoarding.Login.route
            }
        }
    }
}


enum class AuthType {
    HOUSE_COWORK,
    GOOGLE,
}