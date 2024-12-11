package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.calendar.CalendarDataSource
import com.polly.housecowork.dataclass.AssignedTask
import com.polly.housecowork.dataclass.CalendarUiModel
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.usecase.task.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileUiState {
    data class ViewMode(
        val profileInfo: ProfileInfo? = null,
        val calendarUiModel: CalendarUiModel? = null,
        val assignedTasks: List<AssignedTask> = emptyList()
    ) : ProfileUiState

    data class EditMode(
        val isEditMode: Boolean = false,
        val editName: String = "",
        val editBio: String = "",
        val editProfileImage: String = "",
        val errState: ErrState = ErrState(),
    ) : ProfileUiState
}

data class ErrState(
    var nameErr: Boolean = false,
    var bioErr: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: DefaultProfileRepository,
    private val taskUseCase: TaskUseCase,
    private val calendarDataSource: CalendarDataSource
): ViewModel() {

    private val _profileViewModeState = MutableStateFlow(ProfileUiState.ViewMode())
    val profileUiState: StateFlow<ProfileUiState.ViewMode> = _profileViewModeState.asStateFlow()

    private val _profileEditModeState = MutableStateFlow(ProfileUiState.EditMode())
    val profileEditModeState: StateFlow<ProfileUiState.EditMode> = _profileEditModeState.asStateFlow()

    init {
        fetchProfileInfo()
        getAssignedTasks()
        getCalendarUiModel()
    }


    fun updateProfileName(name: String){
        _profileEditModeState.update {
            it.copy(editName = name)
        }
    }

    fun updateProfileBio(bio: String){
        _profileEditModeState.update {
            it.copy(editBio = bio)
        }

    }

    private fun fetchProfileInfo(fetchRemote: Boolean = false)  {
        viewModelScope.launch {
            val profile = profileRepository.getUserProfile(fetchRemote)
            _profileViewModeState.update {
                it.copy(profileInfo = profile)
            }
        }

    }


    fun getAssignedTasks(){
        viewModelScope.launch {
            taskUseCase.transformTaskUseCase.invoke(
                AssigneeStatusType.ACCEPTED,
                fetchRemote = false
            ).collect { tasksResult ->
                val tasks = tasksResult.getOrNull() ?: emptyList()
                _profileViewModeState.update { state ->
                    state.copy(assignedTasks = tasks)
                }
            }

        }
    }

    fun getCalendarUiModel(){
        viewModelScope.launch {
            _profileViewModeState.update {
                it.copy(calendarUiModel = calendarDataSource.sundayToSaturdayWeek)
            }
        }
    }

    fun changeEditMode(){
        _profileEditModeState.update {
            it.copy(
               isEditMode = !it.isEditMode,
            )
        }
    }

    fun checkEditProfileInfo(){
        if(_profileEditModeState.value.isEditMode) return
        _profileEditModeState.update {
            it.copy(
                errState = ErrState(
                    nameErr = it.editName.length > 20 || it.editName.isEmpty(),
                    bioErr = it.editBio.length > 200 || it.editBio.isEmpty()
                )
            )
        }

    }
}