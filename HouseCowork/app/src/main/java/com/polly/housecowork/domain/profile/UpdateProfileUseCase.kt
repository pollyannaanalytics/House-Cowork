package com.polly.housecowork.domain.profile

import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.network.model.ProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: DefaultProfileRepository
) {
    private var userProfile: Profile? = null

    suspend operator fun invoke(
        name: String = "",
        nickname: String,
        bio: String,
        bankAccount: String = "",
        imageUri: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        getUserProfile()
        val userName = name.ifBlank { userProfile?.name ?: "" }
        val userNickname = nickname
        val userBio = bio
        val userBankAccount = bankAccount.ifBlank { userProfile?.bankAccount ?: "" }

        //todo: imageUri?
        val userImageUri = ""

        val profileRequest = ProfileRequest(
            name = userName,
            nickName = userNickname,
            avatar = userImageUri,
            //todo: bio?
            bankAccount = userBankAccount
        )

        return@withContext profileRepository.updateProfile(profileRequest)
    }

    private suspend fun getUserProfile() {

        userProfile = profileRepository.getUserProfile()
    }
}
