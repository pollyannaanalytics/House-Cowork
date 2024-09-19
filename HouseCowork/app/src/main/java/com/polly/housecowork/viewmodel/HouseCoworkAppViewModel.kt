package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.prefs.PrefsLicense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HouseCoworkAppViewModel @Inject constructor(
    private val profileRepository: DefaultProfileRepository,
    private val license: PrefsLicense

) : ViewModel(){

    private val _profileInfo = MutableStateFlow<ProfileInfo?>(null)
    val profileInfo = _profileInfo.asStateFlow()

    init {
        getUserProfileInfo()
    }

    fun getUserProfileInfo() {
        viewModelScope.launch {
            _profileInfo.value = profileRepository.getProfileById(true, license.userId)

        }
    }




}