package com.polly.housecowork.domain.profile

import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.model.profile.DefaultProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: DefaultProfileRepository) {

    suspend fun invoke(profileId: Int? = null): Profile {
       profileId?.let {
              return profileRepository.getProfileById(it)
       }?: return profileRepository.getUserProfile()

    }
}