package com.polly.housecowork.model.profile

import com.polly.housecowork.dataclass.ProfileInfo
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfileById(profileId: Int): Flow<ProfileInfo>
    suspend fun getAllProfiles(): Flow<List<ProfileInfo>>
    suspend fun updateProfile(profile: ProfileInfo)
    suspend fun deleteProfileById(profileId: Int)
}