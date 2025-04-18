package com.polly.housecowork.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.domain.profile.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileUiState {
    data class ViewMode(
        val profile: Profile? = null,
    ) : ProfileUiState

    data class EditMode(
        val isEditMode: Boolean = false,
        val editName: String = "",
        val editBio: String = "",
        val imageUri: Uri? = null,
        val isNameValid: Boolean = true,
        val isBioValid: Boolean = true,
    ) {
        val isValid: Boolean
            get() = isNameValid && isBioValid
    }
}

data class ErrState(
    var nameErr: Boolean = false,
    var bioErr: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _profileViewModeState = MutableStateFlow(ProfileUiState.ViewMode())
    val profileUiState = _profileViewModeState.asStateFlow()

    private val _profileEditModeState = MutableStateFlow(ProfileUiState.EditMode())
    val profileEditModeState: StateFlow<ProfileUiState.EditMode> =
        _profileEditModeState.asStateFlow()


    val errState = _profileEditModeState.map { state ->
        ErrState(
            nameErr = !state.isNameValid,
            bioErr = !state.isBioValid
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ErrState()
    )

    init {
        fetchProfileInfo()
    }


    fun updateProfileName(name: String) {
        _profileEditModeState.update {
            it.copy(editName = name)
        }
    }

    fun updateProfileBio(bio: String) {
        _profileEditModeState.update {
            it.copy(editBio = bio)
        }

    }

    private fun fetchProfileInfo() {
        viewModelScope.launch {
            val profile = profileUseCase.getProfileUseCase.invoke()
            Log.d("ProfileViewModel", "fetchProfileInfo: ${profile}")
            _profileViewModeState.update {
                it.copy(profile = profile)
            }
        }

    }


    fun changeEditMode() {
        if (_profileEditModeState.value.isEditMode) {
            checkEditProfileInfo()
            val state = _profileEditModeState.value
            if (!state.isValid) return

            updateProfileInfo()
        } else {
            _profileEditModeState.update {
                it.copy(
                    editName = profileUiState.value.profile?.name ?: "",
                    editBio = profileUiState.value.profile?.bio ?: "",
                    imageUri = Uri.parse(profileUiState.value.profile?.avatar ?: "")
                )
            }
        }
        _profileEditModeState.update {
            it.copy(isEditMode = !it.isEditMode)
        }
    }

    private fun updateProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            checkEditProfileInfo()
            val state = _profileEditModeState.value

            profileUseCase.updateProfileUseCase.invoke(
                name = state.editName,
                bio = state.editBio,
                bankAccount = "1234567890",
                imageUri = state.imageUri.toString()
            )

            fetchProfileInfo()
        }
    }


    private fun String.isNameValid(): Boolean {
        return length <= 20 && isNotEmpty()
    }

    private fun String.isBioValid(): Boolean {
        return length <= 200
    }

    private fun checkEditProfileInfo() {
        _profileEditModeState.update {
            it.copy(
                isNameValid = it.editName.isNameValid(),
                isBioValid = it.editBio.isBioValid(),
            )
        }
    }

    fun uploadProfilePhoto(imageUri: Uri) {
        _profileEditModeState.update {
            it.copy(
                imageUri = imageUri
            )
        }
    }
}