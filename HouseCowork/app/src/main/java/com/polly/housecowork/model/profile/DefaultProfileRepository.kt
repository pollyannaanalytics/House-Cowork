package com.polly.housecowork.model.profile

import android.util.Log
import com.polly.housecowork.network.ConnectionUtils
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.network.model.ProfileInfoResponse
import com.polly.housecowork.network.model.ProfileRequest
import com.polly.housecowork.network.model.User
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
    suspend fun getUserProfile(): Profile = withContext(Dispatchers.IO) {
        getProfileById(prefsLicense.userId)
    }

    suspend fun getProfileById(
        profileId: Int,
    ): Profile = withContext(Dispatchers.IO) {
        if (!connectionUtils.isNetworkAvailable()) {
            return@withContext localDataSource.getProfileById(profileId)
        }
        val result = remoteDataSource.getProfileById(profileId)
        when {
            result.isSuccessful -> {
                result.body()?.let { body: ProfileInfoResponse ->
                    val profile = body.user.toProfile()
                    localDataSource.updateProfile(profile)

                    return@let profile
                } ?: return@withContext localDataSource.getProfileById(profileId)
            }

            else -> {
                Log.e("ProfileRepository", "Failed to fetch profile")
                return@withContext localDataSource.getProfileById(profileId)
            }
        }


    }

    suspend fun getAllProfiles(
    ): List<Profile> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getAllProfiles()
    }


    suspend fun updateProfile(profile: ProfileRequest): Result<Unit> {
        val response = remoteDataSource.updateProfile(profile)

        when {
            response.isSuccessful -> {
                response.body()?.let { body: ProfileInfoResponse ->
                    Log.d("ProfileRepository", "updateProfile: $body")
                    val updatedProfile = body.user.toProfile()

                    localDataSource.updateProfile(updatedProfile)
                }
                return Result.success(Unit)
            }

            else -> {
                Log.e("ProfileRepository", "Failed to update profile")
                return Result.failure(Exception("Failed to update profile"))
            }
        }
    }

    private fun User.toProfile(): Profile {
        return Profile(
            id = id,
            name = name,
            email = email,
            avatar = avatar
        )
    }

}