package com.polly.housecowork.model.auth

import android.util.Log
import com.polly.housecowork.network.AuthApiService
import com.polly.housecowork.network.model.AuthResponse
import com.polly.housecowork.network.model.SignInRequest
import com.polly.housecowork.network.model.SignUpRequest
import retrofit2.Response
import javax.inject.Inject

class HouseCoworkAuthStrategy @Inject constructor(
    private val authApiService: AuthApiService
) : AuthStrategy {
    override suspend fun signUp(signUpRequest: SignUpRequest): Response<AuthResponse> {
        val response = authApiService.signUp(signUpRequest)
        Log.d("HouseCoworkAuthStrategy", "signUp: ${response.code()}")
        return response
    }

    override suspend fun signIn(signInRequest: SignInRequest): Response<AuthResponse> {
        val response = authApiService.signIn(signInRequest)
        Log.d("HouseCoworkAuthStrategy", "signIn: ${response.code()}")
        return response
    }
}