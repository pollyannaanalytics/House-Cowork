package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import com.polly.housecowork.model.profile.DefaultProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class HouseCoworkAppViewModel @Inject constructor(
    private val profileRepository: DefaultProfileRepository
) : ViewModel(){

    private val _profileInfo = MutableStateFlow(profileRepository.getProfile())
    val profileInfo = _profileInfo




}