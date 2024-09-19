package com.polly.housecowork.model.task.usecase

import com.polly.housecowork.model.profile.DefaultProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: DefaultProfileRepository
) {
    suspend fun invoke(fetchRemote: Boolean, profileId: Int) =
        profileRepository.getProfileById( fetchRemote, profileId)
}