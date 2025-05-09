package com.polly.housecowork.network.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("/v1/users/{id}/profile")
    suspend fun getUserInfo(
        @Path("id") userId: Int
    ): Response<UserResponse>
}