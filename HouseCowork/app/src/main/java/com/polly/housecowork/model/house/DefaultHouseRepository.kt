package com.polly.housecowork.model.house

import android.util.Log
import com.polly.housecowork.local.HouseDao
import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.prefs.PrefsLicense
import javax.inject.Inject

class DefaultHouseRepository @Inject constructor(
    private val houseRemoteDataSource: HouseRemoteDataSource,
    private val houseLocalDataSource: HouseDao,
    private val prefsLicense: PrefsLicense

) {
    suspend fun createHouse(request: HouseRequest): Result<Unit> {
        val response = houseRemoteDataSource.createHouse(request)
        Log.d("HouseRepository", "Response Code: ${response.code()}")
        if(response.isSuccessful){
            Log.d("HouseRepository", "House created successfully")
            prefsLicense.houseId = response.body()?.houseResponse?.id ?: 0
            return Result.success(Unit)
        }
        else{
            Log.d("HouseRepository", "House creation failed")
            Log.d("HouseRepository", "Response Body: ${response.errorBody()?.string()}")
            return Result.failure(Exception("House creation failed"))
        }

    }


}