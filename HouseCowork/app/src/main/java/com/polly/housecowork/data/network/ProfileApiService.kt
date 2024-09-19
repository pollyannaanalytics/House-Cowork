package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.ProfileInfo

interface ProfileApiService {

    suspend fun getProfileById(id: Int): ProfileInfo
    suspend fun getAllProfiles(): List<ProfileInfo>
}


class MockProfileApiService : ProfileApiService {
    private val mockProfileInfo = ProfileInfo(
        1,
        "Mock Profile",
        "Mock Profile Description",
        "https://mock.com",
        "23232323",
        "2021-10-10",
        123232323
    )

    override suspend fun getProfileById(id: Int): ProfileInfo{
        return mockProfileInfo
    }

    override suspend fun getAllProfiles(): List<ProfileInfo> {
        return listOf(mockProfileInfo)
    }
}