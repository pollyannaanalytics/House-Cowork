package com.polly.housecowork.model.auth

import android.util.Log
import com.polly.housecowork.data.network.AuthApiService
import com.polly.housecowork.dataclass.AuthData
import com.polly.housecowork.dataclass.SignInRequest
import com.polly.housecowork.dataclass.SignUpRequest
import retrofit2.Response
import javax.inject.Inject

class HouseCoworkAuthStrategy @Inject constructor(
    private val authApiService: AuthApiService
) : AuthStrategy {
    override suspend fun signUp(signUpRequest: SignUpRequest): Response<AuthData> {
        val response = authApiService.signUp(signUpRequest)
        Log.d("HouseCoworkAuthStrategy", "signUp: ${response.code()}")
        return response
    }

    override suspend fun signIn(signInRequest: SignInRequest): Response<AuthData> {
        val response = authApiService.signIn(signInRequest)
        Log.d("HouseCoworkAuthStrategy", "signIn: ${response.code()}")
        return response
    }
}