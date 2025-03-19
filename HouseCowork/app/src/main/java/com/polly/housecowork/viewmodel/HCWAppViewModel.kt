package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import com.polly.housecowork.model.auth.OnboardingState
import com.polly.housecowork.model.auth.OnboardingState.Companion.toPrefKey
import com.polly.housecowork.prefs.PrefsLicense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HCWAppViewModel @Inject constructor(
    private val prefsLicense: PrefsLicense
) : ViewModel() {

    private val _userNextState = MutableStateFlow<OnboardingState>(OnboardingState.Auth.Incomplete)
    val userNextState = _userNextState.asStateFlow()

    init {
        checkUserState()
    }

    private fun checkUserState() {
        val token = prefsLicense.token
        val userId = prefsLicense.userId

        val onBoardingStatus = prefsLicense.onboardingState

        _userNextState.value = when {
            // todo: need to discuss about how to check if user has signed up but not completed profile
            token.isEmpty() || userId == 0 -> OnboardingState.Onboarding.SignUp
            else -> OnboardingState.Onboarding.CompleteProfile
        }
    }

}