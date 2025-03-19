package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.compose.createtask.dataclass.CreateTaskInput
import com.polly.housecowork.compose.createtask.dataclass.ErrorState
import com.polly.housecowork.dataclass.CreateTaskState
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.model.calendar.CalendarRepository
import com.polly.housecowork.model.calendar.CalendarState
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.model.task.DefaultTaskRepository
import com.polly.housecowork.ui.utils.AccessLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: DefaultTaskRepository,
    private val calendarRepository: CalendarRepository,
    private val profileRepository: DefaultProfileRepository
) : ViewModel() {

    private var allUsers: List<Profile> = emptyList()

    private var _errorState = MutableStateFlow(ErrorState())
    val errorState = _errorState.asStateFlow()

    private var _taskUiState = MutableStateFlow(CreateTaskState())
    val taskUiState = _taskUiState.asStateFlow()

    private val _isPublic = MutableStateFlow(true)
    val isPublic = _isPublic.asStateFlow()

    private val _calendarState = MutableStateFlow(
        CalendarState(
            monthData = calendarRepository.getCurrentMonth(),
            monthTitle = calendarRepository.getCurrentMonthTitle()
        )
    )
    val calendarState = _calendarState.asStateFlow()
    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            allUsers = profileRepository.getAllProfiles()
            val selectableUsers = listOf("everyone") + allUsers.map { it.name }
            _taskUiState.update {
                it.copy(assignedUser = allUsers.toMutableList())
                it.copy(selectableUsers = selectableUsers)
            }
        }
    }

    fun getPreviousMonth() {
        _calendarState.update {
            it.copy(
                monthData = calendarRepository.previousMonth(),
                monthTitle = calendarRepository.getCurrentMonthTitle()
            )
        }
    }

    fun getNextMonth() {
        _calendarState.update {
            it.copy(
                monthData = calendarRepository.nextMonth(),
                monthTitle = calendarRepository.getCurrentMonthTitle()
            )
        }
    }

    fun setTaskTitle(title: String) {
        _taskUiState.update {
            it.copy(title = title)
        }
    }

    fun setTaskDescription(description: String) {
        _taskUiState.update {
            it.copy(description = description)
        }
    }

    fun setAssignedUser(selectedName: String) {
        val newAssignedUser = when (selectedName) {
            "everyone" -> allUsers
            else -> allUsers.filter { it.name == selectedName }
        }

        _taskUiState.update {
            it.copy(assignedUser = newAssignedUser.toMutableList())
        }
    }

    fun setDueTime(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        _taskUiState.update {
            it.copy(
                dueTime = calendar.timeInMillis,
                dueHour = hour,
                dueMinute = minute
            )
        }
    }


    fun checkFinish() {
        if (!validateTask(_taskUiState.value)) return

        val createTaskInput = CreateTaskInput(
            taskTitle = _taskUiState.value.title,
            taskDescription = _taskUiState.value.description,
            taskAccessLevel = _taskUiState.value.accessLevel.level,
            assignees = _taskUiState.value.assignedUser.map { it.id },
            taskDueTime = _taskUiState.value.dueTime
        )

        viewModelScope.launch {
            taskRepository.createTask(createTaskInput)
        }
    }

    fun validateTask(state: CreateTaskState): Boolean {
        val titleError = state.title.isEmpty()
        val dueTimeError = state.dueTime < System.currentTimeMillis()
        _taskUiState.update {
            it.copy(errors = ErrorState(titleError, dueTimeError))
        }
        return titleError || dueTimeError
    }

    fun clearTaskEmptyError() {
        _errorState.value.titleError = false
    }

    fun setPublicLevel(isPublic : Boolean) {
        _taskUiState.update {
            _isPublic.value = isPublic
            it.copy(accessLevel =  if(isPublic) AccessLevel.PUBLIC else AccessLevel.PRIVATE)
        }
    }

    fun clearDueTimeError() {
        _errorState.value.dueTimeError = false
    }
}