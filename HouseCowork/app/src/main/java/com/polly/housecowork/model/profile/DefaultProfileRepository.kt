package com.polly.housecowork.model.profile

import com.polly.housecowork.dataclass.ProfileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: ProfileDao
): ProfileRepository{

    override suspend fun getProfileById(profileId: Int): Flow<ProfileInfo> = flow {
        var profile = localDataSource.getProfileById(profileId)
        if (profile == null) {
            profile = remoteDataSource.getProfileById(profileId)
            emit(profile)
            localDataSource.insertProfile(profile)
        }
        emit(profile)
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllProfiles(): Flow<List<ProfileInfo>> = flow {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(profile: ProfileInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProfileById(profileId: Int) {
        TODO("Not yet implemented")
    }
}