package com.polly.housecowork.model.profile

import com.polly.housecowork.dataclass.ProfileInfo
import javax.inject.Inject

class DefaultProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: ProfileDao
): ProfileRepository{
    fun getProfile() = ProfileInfo(
        id = 1,
        name = "Polly",
        nickName = "Polly",
        avatar = "https://avatars.githubusercontent.com/u/12345678?v=4",
        bankAccount = "1234567890"
        )

    override fun getProfileById(profileId: Int): ProfileInfo {
        if(localDataSource.getProfileById(profileId) == null){
            val profile = remoteDataSource.getProfileById(profileId)
            localDataSource.insertProfile(profile)
        }
    }p

    override fun getAllProfiles(): List<ProfileInfo> {
        TODO("Not yet implemented")
    }

    override fun updateProfile(profile: ProfileInfo) {
        TODO("Not yet implemented")
    }

    override fun deleteProfileById(profileId: Int) {
        TODO("Not yet implemented")
    }
}