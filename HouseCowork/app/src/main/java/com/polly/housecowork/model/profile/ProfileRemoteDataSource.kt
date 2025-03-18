package com.polly.housecowork.model.profile

import android.util.Log
import com.polly.housecowork.network.ProfileApiService
import com.polly.housecowork.network.model.ProfileInfoResponse
import com.polly.housecowork.network.model.ProfileRequest
import retrofit2.Response
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val apiService: ProfileApiService
) {
    suspend fun getProfileById(profileId: Int): Response<ProfileInfoResponse> {
        val response = apiService.getProfileById(profileId)
        Log.d("ProfileRemoteDataSource", "getProfileById: ${response.code()}")
        return apiService.getProfileById(profileId)
    }

    suspend fun updateProfile(profileRequest: ProfileRequest): Response<ProfileInfoResponse> {
        return apiService.updateProfile(profileRequest)
    }
}