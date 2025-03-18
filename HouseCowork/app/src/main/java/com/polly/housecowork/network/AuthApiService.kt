package com.polly.housecowork.network

import retrofit2.http.Body
import retrofit2.http.POST
import com.polly.housecowork.network.model.SignUpRequest
import com.polly.housecowork.network.model.AuthResponse
import com.polly.housecowork.network.model.SignInRequest
import retrofit2.Response

interface AuthApiService {
    @POST("auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<AuthResponse>

    @POST("auth/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<AuthResponse>

}
