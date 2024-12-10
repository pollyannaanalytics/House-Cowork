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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: DefaultProfileRepository,
    private val taskUseCase: TaskUseCase,
    private val calendarDataSource: CalendarDataSource
): ViewModel() {

    private val _profileInfo = MutableStateFlow<ProfileInfo?>(null)
    val profileInfo: StateFlow<ProfileInfo?> = _profileInfo
        .onStart {
           profileRepository.getUserProfile(true)
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _calendarUiModel = MutableStateFlow(calendarDataSource.sundayToSaturdayWeek)
    val calendarUiModel: StateFlow<CalendarUiModel> = _calendarUiModel
        .onStart {
            calendarDataSource.sundayToSaturdayWeek
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = calendarDataSource.sundayToSaturdayWeek
        )


    private val _assignedTasks = MutableStateFlow(emptyList<AssignedTask>())
    val assignedTasks = _assignedTasks.asStateFlow()

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode = _isEditMode.asStateFlow()


    fun updateProfileName(name: String){

    }

    fun updateProfileBio(bio: String){

    }


    fun getAssignedTasks(userId: Int){
        viewModelScope.launch {
            taskUseCase.transformTaskUseCase.invoke(
                userId,
                AssigneeStatusType.ACCEPTED,
                true
            )
        }
    }

    fun changeEditMode(){
        _isEditMode.value = !_isEditMode.value
    }





}