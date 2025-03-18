package com.polly.housecowork.model.auth

import android.util.Log
import com.polly.housecowork.network.model.SignInRequest
import com.polly.housecowork.network.model.SignUpRequest
import com.polly.housecowork.model.auth.OnboardingState.Companion.toPrefKey
import com.polly.housecowork.prefs.PrefsLicense
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val prefsLicense: PrefsLicense,
    private val authStrategies: Map<AuthType, @JvmSuppressWildcards AuthStrategy>
) {
    suspend fun signUp(authType: AuthType, signUpRequest: SignUpRequest): OnboardingState {
        val response = authStrategies[authType]?.signUp(signUpRequest)

        Log.d(
            "AuthRepository",
            "prefsLicense.token: ${prefsLicense.token}, accessToken: ${response?.body()?.accessToken}"
        )
        response?.takeIf { it.isSuccessful }?.let {
            coroutineScope {
                prefsLicense.userId = it.body()?.user?.id ?: 0
                prefsLicense.token = it.body()?.accessToken ?: ""
                prefsLicense.onboardingState = OnboardingState.Onboarding.SignUp.toPrefKey()
                Log.d(
                    "AuthRepository",
                    "prefsLicense.token: ${prefsLicense.token}, userId: ${prefsLicense.userId}"
                )
            }
            return OnboardingState.Auth.Complete
        } ?: return OnboardingState.Auth.Incomplete
    }

    suspend fun signIn(authType: AuthType, signInRequest: SignInRequest): OnboardingState {
        val response = authStrategies[authType]?.signIn(signInRequest)
        response?.takeIf { it.isSuccessful }?.let {
            coroutineScope {
                prefsLicense.token = it.body()?.accessToken ?: ""
                prefsLicense.onboardingState = OnboardingState.Auth.Complete.toPrefKey()
            }
            return OnboardingState.Auth.Complete
        } ?: return OnboardingState.Auth.Incomplete
    }

    fun logout() {
        prefsLicense.token = ""
        prefsLicense.onboardingState = OnboardingState.Auth.Incomplete.toPrefKey()
    }


}