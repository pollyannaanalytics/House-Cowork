package com.polly.housecowork.network

import com.polly.housecowork.network.model.GetOwnHousesResponse
import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.network.model.HouseCreateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HouseApiService {
    @POST("houses")
    suspend fun createHouse(
        @Body request: HouseRequest
    ): Response<HouseCreateResponse>

    @GET("houses/own")
    suspend fun getHouses(): Response<GetOwnHousesResponse>

    @PUT("houses/{houseId}")
    suspend fun updateHouse(
        houseId: Int,
        @Body request: HouseRequest
    ): Response<HouseCreateResponse>

    @GET("/v1/houses/{houseId}")
    suspend fun getHouseInfo(
        @Path("houseId") houseId: Int
    ): Response<HouseCreateResponse>
}