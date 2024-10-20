package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.ProfileInfo

interface ProfileApiService {
    suspend fun getProfileById(id: Int): ProfileInfo
    suspend fun getAllProfiles(): List<ProfileInfo>
}


class MockProfileApiService : ProfileApiService {
    private val mockProfileInfo = ProfileInfo(
        name = "Mock Profile",
        nickName = "Mock Profile Description",
        avatar = "https://mock.com",
        bankAccount = "23232323",
        email = "pinyunwuu@gmail.com",
        updateTime = 123232323
    )

    override suspend fun getProfileById(id: Int): ProfileInfo{
        return mockProfileInfo
    }

    override suspend fun getAllProfiles(): List<ProfileInfo> {
        return List(10) { mockProfileInfo }
    }
}