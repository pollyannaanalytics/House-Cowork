package com.polly.housecowork.network

import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.network.model.HouseCreateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HouseApiService {
    @POST("houses")
    suspend fun createHouse(
        @Body request: HouseRequest
    ): Response<HouseCreateResponse>

    @GET("houses/own")
    suspend fun getOwnHouse(): Response<HouseCreateResponse>

    @PUT("houses/{houseId}")
    suspend fun updateHouse(
        houseId: Int,
        @Body request: HouseRequest
    ): Response<HouseCreateResponse>


}