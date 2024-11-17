package com.polly.housecowork.model.profile

import com.polly.housecowork.data.network.ConnectionUtils
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.prefs.PrefsLicense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: ProfileDao,
    private val connectionUtils: ConnectionUtils,
    private val prefsLicense: PrefsLicense,
){
    suspend fun getUserProfile(
        fetchRemote: Boolean
    ): ProfileInfo = withContext(Dispatchers.IO) {
        getProfileById(prefsLicense.userId, fetchRemote)
    }

    suspend fun getProfileById(
        profileId: Int,
        fetchRemote: Boolean
    ): ProfileInfo = withContext(Dispatchers.IO) {
            if (!fetchRemote || !connectionUtils.isNetworkAvailable()) {
                return@withContext localDataSource.getProfileById(profileId)
            }
            val result = remoteDataSource.getProfileById(profileId)
            result.fold(
                onSuccess = { profile ->
                    localDataSource.insertProfile(profile)
                    return@withContext profile
                },
                onFailure = { error ->
                    throw error
                }
            )


    }
    suspend fun getAllProfiles(
        fetchRemote: Boolean
    ): List<ProfileInfo> = withContext(Dispatchers.IO) {
        if (!fetchRemote || !connectionUtils.isNetworkAvailable()) {
            return@withContext localDataSource.getAllProfiles()
        }
        val result = remoteDataSource.getAllProfiles()
        result.fold(
            onSuccess = { profiles ->
                localDataSource.upsertAllProfiles(profiles)
                return@withContext profiles
            },
            onFailure = { error ->
                throw error
            }
        )
    }

 suspend fun updateProfile(profile: ProfileInfo) {
        TODO("Not yet implemented")
    }

   suspend fun deleteProfileById(profileId: Int) {
        TODO("Not yet implemented")
    }
}