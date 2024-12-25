package com.polly.housecowork.model.auth

import android.util.Log
import com.polly.housecowork.dataclass.SignInRequest
import com.polly.housecowork.dataclass.SignUpRequest
import com.polly.housecowork.prefs.PrefsLicense
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val prefsLicense: PrefsLicense,
    private val authStrategies: Map<AuthType, @JvmSuppressWildcards AuthStrategy>
) {
    fun checkAuthState(): AuthState {
        return if (prefsLicense.token.isNotEmpty()) AuthState.Login(prefsLicense.userId) else AuthState.LogOut
    }

    suspend fun signUp(authType: AuthType, signUpRequest: SignUpRequest): AuthState {
        val response = authStrategies[authType]?.signUp(signUpRequest)

        Log.d("AuthRepository", "prefsLicense.token: ${prefsLicense.token}, accessToken: ${response?.body()?.accessToken}")
        response?.takeIf { it.isSuccessful }?.let {
            coroutineScope {
                prefsLicense.userId = it.body()?.user?.id ?: 0
                prefsLicense.token = it.body()?.accessToken ?: ""
                Log.d("AuthRepository", "prefsLicense.token: ${prefsLicense.token}, userId: ${prefsLicense.userId}")
            }
            return AuthState.Login(prefsLicense.userId)
        } ?: return AuthState.UnAuthenticated
    }

    suspend fun signIn(authType: AuthType, signInRequest: SignInRequest): AuthState {
        val response = authStrategies[authType]?.signIn(signInRequest)
        response?.takeIf { it.isSuccessful }?.let {
            coroutineScope {
                prefsLicense.token = it.body()?.accessToken ?: ""
            }
            return AuthState.Login(prefsLicense.userId)
        } ?: return AuthState.LogOut
    }

    fun logout() {
        prefsLicense.token = ""
    }


}