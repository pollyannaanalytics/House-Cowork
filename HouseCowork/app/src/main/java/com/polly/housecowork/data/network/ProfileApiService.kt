package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.ProfileInfo

interface ProfileApiService {
    suspend fun getProfileById(id: Int): Result<ProfileInfo>
    suspend fun getAllProfiles(): Result<List<ProfileInfo>>
}


class MockProfileApiService : ProfileApiService {
    private val mockProfileInfo = ProfileInfo(
        name = "Mock Profile",
        nickName = "Mock Profile Description",
        imageUrl = "https://mock.com",
        bankAccount = "23232323",
        email = "pinyunwuu@gmail.com",
        updateTime = 123232323,
        bio = "i am a software engineer"
    )

    override suspend fun getProfileById(id: Int): Result<ProfileInfo>{
        return Result.success(mockProfileInfo)
    }

    override suspend fun getAllProfiles(): Result<List<ProfileInfo>> {
        return Result.success(List(10) { mockProfileInfo })
    }
}