package com.polly.housecowork.model.house

import com.polly.housecowork.network.HouseApiService
import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.network.model.HouseCreateResponse
import retrofit2.Response
import javax.inject.Inject

class HouseRemoteDataSource @Inject constructor(
    private val houseApiService: HouseApiService
) {

    suspend fun createHouse(request: HouseRequest): Response<HouseCreateResponse> {
        return houseApiService.createHouse(request)
    }

    suspend fun getHouse(houseId: Int): Response<HouseCreateResponse> {
        return houseApiService.getHouseInfo(houseId)
    }

}