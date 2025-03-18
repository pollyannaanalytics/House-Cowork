package com.polly.housecowork.network

import com.polly.housecowork.network.model.ProfileInfoResponse
import com.polly.housecowork.network.model.ProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApiService {

    @GET("users/{id}/profile")
    suspend fun getProfileById(@Path("id") id: Int): Response<ProfileInfoResponse>

    @PUT("users/profile")
    suspend fun updateProfile(@Body profileRequest: ProfileRequest): Response<ProfileInfoResponse>

}