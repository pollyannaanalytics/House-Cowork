package com.polly.housecowork.model.house

import com.polly.housecowork.network.HouseApiService
import com.polly.housecowork.network.model.GetOwnHousesResponse
import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.network.model.HouseCreateResponse
import com.polly.housecowork.network.model.UserApiService
import com.polly.housecowork.network.model.UserResponse
import retrofit2.Response
import javax.inject.Inject

class HouseRemoteDataSource @Inject constructor(
    private val houseApiService: HouseApiService,
    private val userApiService: UserApiService
) {

    suspend fun createHouse(request: HouseRequest): Response<HouseCreateResponse> {
        return houseApiService.createHouse(request)
    }

    suspend fun getHouses(): Response<GetOwnHousesResponse> {
        return houseApiService.getHouses()
    }

    suspend fun getHouse(houseId: Int): Response<HouseCreateResponse> {
        return houseApiService.getHouseInfo(houseId)
    }

    suspend fun getUser(userId: Int): Response<UserResponse> {
        return userApiService.getUserInfo(userId)
    }

}