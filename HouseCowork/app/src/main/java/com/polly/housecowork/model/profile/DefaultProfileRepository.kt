package com.polly.housecowork.model.profile

import com.polly.housecowork.data.network.ConnectionUtils
import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.ProfileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: ProfileDao,
    private val connectionUtils: ConnectionUtils
){
    suspend fun getProfileById(
        fetchRemote: Boolean,
        profileId: Int
    ): ProfileInfo = withContext(Dispatchers.IO) {
            if (!fetchRemote || connectionUtils.isNetworkAvailable()) {
                return@withContext localDataSource.getProfileById(profileId)
            }
            val result = remoteDataSource.getProfileById(profileId)
            if (result is ApiResult.Success) {
                val profile = result.data
                localDataSource.insertProfile(profile)
                return@withContext profile
            }else{
                throw Exception("Profile not found")
            }


    }
    suspend fun getAllProfiles(
        fetchRemote: Boolean
    ): Flow<List<ProfileInfo>> = flow {
        if(fetchRemote && connectionUtils.isNetworkAvailable()){
            val result = remoteDataSource.getAllProfiles()
            if(result is ApiResult.Success){
                val profiles = result.data
                emit(profiles)
            }
        } else {
            val profiles = localDataSource.getAllProfiles()
            if(profiles.isNotEmpty()){
                emit(profiles)
            }
        }
    }.flowOn(Dispatchers.IO)

 suspend fun updateProfile(profile: ProfileInfo) {
        TODO("Not yet implemented")
    }

   suspend fun deleteProfileById(profileId: Int) {
        TODO("Not yet implemented")
    }
}