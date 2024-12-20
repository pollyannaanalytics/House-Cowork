package com.polly.housecowork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.model.auth.AuthRepository
import com.polly.housecowork.model.auth.AuthState
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.prefs.PrefsLicense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HCWAppViewModel @Inject constructor(
    private val profileRepository: DefaultProfileRepository,
    private val authRepository: AuthRepository,
    private val license: PrefsLicense

) : ViewModel(){

    private val _profileInfo = MutableStateFlow<ProfileInfo?>(null)
    val profileInfo = _profileInfo.asStateFlow()

    private val _authState = MutableStateFlow(checkAuthState())
    val authState = _authState.asStateFlow()

    init {
        getUserProfileInfo()
    }

    private fun getUserProfileInfo() {
        viewModelScope.launch {
            _profileInfo.value = profileRepository.getProfileById(
                license.userId, true
            )
        }
    }

    private fun checkAuthState(): AuthState {
        val state = authRepository.checkAuthState()
        Log.d("HCWAppViewModel", "checkAuthState: $state")
        return authRepository.checkAuthState()
    }
}