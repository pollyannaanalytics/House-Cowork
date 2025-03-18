package com.polly.housecowork.model.auth

import com.polly.housecowork.network.model.AuthResponse
import com.polly.housecowork.network.model.SignInRequest
import com.polly.housecowork.network.model.SignUpRequest
import retrofit2.Response

interface AuthStrategy {
    suspend fun signUp(signUpRequest: SignUpRequest): Response<AuthResponse>
    suspend fun signIn(signInRequest: SignInRequest): Response<AuthResponse>
}