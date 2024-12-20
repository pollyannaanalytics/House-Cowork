package com.polly.housecowork.model.auth

import com.polly.housecowork.dataclass.AuthData
import com.polly.housecowork.dataclass.SignInRequest
import com.polly.housecowork.dataclass.SignUpRequest
import retrofit2.Response

interface AuthStrategy {
    suspend fun signUp(signUpRequest: SignUpRequest): Response<AuthData>
    suspend fun signIn(signInRequest: SignInRequest): Response<AuthData>
}