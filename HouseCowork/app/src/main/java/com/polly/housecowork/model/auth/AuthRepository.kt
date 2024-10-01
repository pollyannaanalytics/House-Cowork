package com.polly.housecowork.model.auth

import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.prefs.PrefsLicense
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val prefsLicense: PrefsLicense,
    private val authStrategies: Map<String, @JvmSuppressWildcards AuthStrategy>
) {

    fun checkAuthState(loginType: String): AuthState{
        if (prefsLicense.userId == 0) return AuthState.LogOut
        return when(authStrategies[loginType]?.checkAuthState(prefsLicense.userId)){
            is ApiResult.Success -> AuthState.Login
            else -> AuthState.UnAuthenticated
        }
    }


    companion object{
        const val HOUSE_COWORK = "housecowork"
    }

}