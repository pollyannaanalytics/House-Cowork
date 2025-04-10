package com.polly.housecowork.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.domain.onboarding.UpdateOnboardingUseCase
import com.polly.housecowork.domain.profile.UpdateProfileUseCase
import com.polly.housecowork.model.auth.OnboardingState
import com.polly.housecowork.ui.utils.compose.AvatarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val updateOnboardingUseCase: UpdateOnboardingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteProfileUiState())
    val uiState: StateFlow<CompleteProfileUiState> = _uiState.asStateFlow()

    fun onBankAccountChanged(bankAccount: String) {
        _uiState.value = _uiState.value.copy(bankAccount = bankAccount)
    }

    fun onBankCodeChanged(bankCode: String) {
        _uiState.value = _uiState.value.copy(bankCode = bankCode)
    }

    fun onAvatarStateChanged(avatarState: AvatarState) {
        _uiState.value = _uiState.value.copy(avatarState = avatarState)
    }

    fun onBankInfoClickedChange() {
        _uiState.value = _uiState.value.copy(isBankInfoClicked = !_uiState.value.isBankInfoClicked)
    }

    fun onBioChanged(bio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                isBioValid = bio.isBioValid(),
                bio = if (bio.isBioValid()) bio else currentState.bio
            )
        }
    }

    fun uploadProfilePhoto(imageUri: Uri) {
        _uiState.update {
            it.copy(
                imageUri = imageUri
            )
        }
    }

    fun onProfileComplete() {
        viewModelScope.launch {
            val uiState = _uiState.value
            val result = updateProfileUseCase(
                bio = uiState.bio,
                bankAccount = uiState.bankAccount,
                imageUri = uiState.imageUri.toString()
            )

            if (result.isSuccess) {
//                updateOnboardingUseCase(OnboardingState.Onboarding.CompleteProfile)
            }
        }
    }

    fun onProfileSkip() {
//        updateOnboardingUseCase(OnboardingState.Onboarding.CompleteProfile)
    }

    private fun String.isBioValid(): Boolean {
        return this.length <= 200
    }

}

data class CompleteProfileUiState(
    val bankAccount: String = "",
    val bankCode: String = "",
    val avatarState: AvatarState = AvatarState.Edit,
    val imageUri: Uri? = null,
    val bio: String = "",
    val isBioValid: Boolean = true,
    val isBankInfoClicked: Boolean = false
)