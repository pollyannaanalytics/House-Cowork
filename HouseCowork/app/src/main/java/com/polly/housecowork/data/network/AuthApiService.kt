package com.polly.housecowork.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import com.polly.housecowork.dataclass.SignUpRequest
import com.polly.housecowork.dataclass.AuthData
import com.polly.housecowork.dataclass.SignInRequest
import okhttp3.Interceptor
import retrofit2.Response

interface AuthApiService {
    @POST("auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<AuthData>

    @POST("auth/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<AuthData>

}
