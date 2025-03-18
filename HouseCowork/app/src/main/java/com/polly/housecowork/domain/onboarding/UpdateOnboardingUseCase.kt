package com.polly.housecowork.domain.onboarding

import com.polly.housecowork.model.auth.OnboardingState
import com.polly.housecowork.model.auth.OnboardingState.Companion.toPrefKey
import com.polly.housecowork.prefs.PrefsLicense
import javax.inject.Inject

class UpdateOnboardingUseCase @Inject constructor(
    private val prefsLicense: PrefsLicense
){
    operator fun invoke(onboardingState: OnboardingState){
        prefsLicense.onboardingState = onboardingState.toPrefKey()
    }
}