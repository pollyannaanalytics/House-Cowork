package com.polly.housecowork.model.profile

import com.polly.housecowork.dataclass.ProfileInfo

interface ProfileRepository {
    fun getProfileById(profileId: Int): ProfileInfo
    fun getAllProfiles(): List<ProfileInfo>
    fun updateProfile(profile: ProfileInfo)
    fun deleteProfileById(profileId: Int)
}