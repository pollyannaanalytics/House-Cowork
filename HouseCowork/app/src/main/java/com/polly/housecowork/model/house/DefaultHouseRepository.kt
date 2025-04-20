package com.polly.housecowork.model.house

import android.util.Log
import com.polly.housecowork.local.HouseDao
import com.polly.housecowork.network.model.House
import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.prefs.PrefsLicense
import javax.inject.Inject

class DefaultHouseRepository @Inject constructor(
    private val houseRemoteDataSource: HouseRemoteDataSource,
    private val houseLocalDataSource: HouseDao,
    private val prefsLicense: PrefsLicense

) {
    suspend fun createHouse(request: HouseRequest): Result<House?> {
        val response = houseRemoteDataSource.createHouse(request)
        if(response.isSuccessful){
            Log.d("HouseRepository", "House created successfully")
            prefsLicense.houseId = response.body()?.house?.id ?: 0
            return Result.success(response.body()?.house)
        }
        else{
            Log.d("HouseRepository", "House creation failed")
            Log.d("HouseRepository", "Response Body: ${response.errorBody()?.string()}")
            return Result.failure(Exception("House creation failed"))
        }

    }

    suspend fun getHouseInfo(houseId: Int): Result<House?> {
        val response = houseRemoteDataSource.getHouse(houseId)
        return if (response.isSuccessful) {
            Log.d("HouseRepository", "Get House Info successfully")
            val body = response.body()
            if (body != null) {
                Result.success(body.house)
            } else {
                Result.failure(Exception("Empty House Info"))
            }
        } else {
            Log.d("HouseRepository", "Get House Info failed")
            Log.d("HouseRepository", "Response Body: ${response.errorBody()?.string()}")
            Result.failure(Exception("Get House Info failed"))
        }
    }
}