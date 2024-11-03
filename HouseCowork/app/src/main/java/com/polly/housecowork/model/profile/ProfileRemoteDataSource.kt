package com.polly.housecowork.model.profile

import com.polly.housecowork.data.network.ProfileApiService
import com.polly.housecowork.dataclass.ProfileInfo
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val apiService: ProfileApiService
) {
    suspend fun getProfileById(profileId: Int): Result<ProfileInfo> {
        try {
            val profile = apiService.getProfileById(profileId)
            return profile
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getAllProfiles(): Result<List<ProfileInfo>> {
        try {
            val profiles = apiService.getAllProfiles()
            return profiles
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}