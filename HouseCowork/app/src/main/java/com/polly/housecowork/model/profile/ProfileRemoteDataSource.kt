package com.polly.housecowork.model.profile

import com.polly.housecowork.data.network.ProfileApiService
import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.ProfileInfo
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val apiService: ProfileApiService
) {
    suspend fun getProfileById(profileId: Int): ApiResult<ProfileInfo> {
        try {
            val profile = apiService.getProfileById(profileId)
            return ApiResult.Success(profile)
        } catch (e: Exception) {
            return ApiResult.Error(e)
        }
    }

    suspend fun getAllProfiles(): ApiResult<List<ProfileInfo>> {
        try {
            val profiles = apiService.getAllProfiles()
            return ApiResult.Success(profiles)
        } catch (e: Exception) {
            return ApiResult.Error(e)
        }
    }
}