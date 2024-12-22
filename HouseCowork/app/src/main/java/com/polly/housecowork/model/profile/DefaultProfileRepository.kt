package com.polly.housecowork.model.profile

import android.util.Log
import com.polly.housecowork.data.network.ConnectionUtils
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.ProfileRequest
import com.polly.housecowork.dataclass.toProfileInfo
import com.polly.housecowork.prefs.PrefsLicense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: ProfileDao,
    private val connectionUtils: ConnectionUtils,
    private val prefsLicense: PrefsLicense,
) {
    suspend fun getUserProfile(): ProfileInfo = withContext(Dispatchers.IO) {
        getProfileById(prefsLicense.userId)
    }

    suspend fun getProfileById(
        profileId: Int,
    ): ProfileInfo = withContext(Dispatchers.IO) {
        if (!connectionUtils.isNetworkAvailable()) {
            return@withContext localDataSource.getProfileById(profileId)
        }
        val result = remoteDataSource.getProfileById(profileId)
        when{
            result.isSuccessful -> {
               result.body()?.let { body ->
                   val profileInfo = body.user.toProfileInfo()
                     localDataSource.updateProfile(profileInfo)
                     return@withContext profileInfo
               }?: return@withContext localDataSource.getProfileById(profileId)
            }
            else -> {
                Log.e("ProfileRepository", "Failed to fetch profile")
                return@withContext localDataSource.getProfileById(profileId)
            }
        }


    }

    suspend fun getAllProfiles(
    ): List<ProfileInfo> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getAllProfiles()
    }


    suspend fun updateProfile(profile: ProfileRequest) {
        val response = remoteDataSource.updateProfile(profile)

        when {
            response.isSuccessful -> {
                response.body()?.let { body ->
                    Log.d("ProfileRepository", "updateProfile: $body")
                    val updatedProfile = body.user.toProfileInfo()

                    localDataSource.updateProfile(updatedProfile)
                }
            }

            else -> {
                Log.e("ProfileRepository", "Failed to update profile")
            }
        }
    }

}