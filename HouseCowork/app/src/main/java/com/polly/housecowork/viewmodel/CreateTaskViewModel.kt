package com.polly.housecowork.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.calendar.CalendarRepository
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.model.task.DefaultTaskRepository
import com.polly.housecowork.ui.utils.AccessLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: DefaultTaskRepository,
    private val profileRepository: DefaultProfileRepository
) : ViewModel() {

    data class ErrorState(
        var titleError: Boolean = false,
        var dueTimeError: Boolean = false
    )

    private val _taskTitle: MutableStateFlow<String> = MutableStateFlow("")

    private val _taskDescription: MutableStateFlow<String> = MutableStateFlow("")
    val taskDescription = _taskDescription.asStateFlow()

    private val _accessLevel: MutableStateFlow<AccessLevel> = MutableStateFlow(AccessLevel.PUBLIC)
    val accessLevel = _accessLevel.asStateFlow()

    private var _assignedUser: MutableStateFlow<MutableList<ProfileInfo>> = MutableStateFlow(mutableListOf())

    private var _selectableAssignees: MutableStateFlow<List<String>> = MutableStateFlow(mutableListOf())
    val selectableAssignees = _selectableAssignees.asStateFlow()

    private var _allUsers: MutableList<ProfileInfo> = mutableListOf()

    private var _dueTime: MutableStateFlow<Long> = MutableStateFlow(System.currentTimeMillis())
    val dueTime = _dueTime.asStateFlow()

    private var _errorState = MutableStateFlow(ErrorState())
    val errorState = _errorState.asStateFlow()

    private var _shouldScrollTop = MutableStateFlow(false)
    val shouldScrollTop = _shouldScrollTop.asStateFlow()

    private var _dueHour = MutableStateFlow(0)
    val dueHour = _dueHour.asStateFlow()

    private var _dueMinute = MutableStateFlow(0)
    val dueMinute = _dueMinute.asStateFlow()

    init {
        setInitialDueTime()
        getAllUsers()
    }

    private fun setInitialDueTime(){
        val simpleHourFormat = SimpleDateFormat("HH")
        val currentTime = System.currentTimeMillis()
        val currentDate = Date(currentTime)
        _dueHour.value = simpleHourFormat.format(currentDate).toInt()
        _dueMinute.value = (currentTime / 1000 / 60 % 60).toInt()
    }

    fun getAllUsers(fetchRemote: Boolean = false) {
        viewModelScope.launch {
            val users = emptyList<ProfileInfo>().toMutableList()
            profileRepository.getAllProfiles(fetchRemote).collect{
                it.map { profile ->
                   users.add(profile)
                }
            }
            _allUsers = users
            _selectableAssignees.value = listOf("everyone" + users.map { it.name })

        }
    }

    fun setTaskTitle(title: String) {
        _taskTitle.value = title
    }
    fun setAssignedUser(selectedName: String) {
       if (selectedName == "everyone") {
           _assignedUser.value = _allUsers
       } else {
           val selectedUser = _allUsers.filter { it.name == selectedName }
           _assignedUser.value.add(selectedUser[0])
       }

    }

    fun setDueDate(dateTimestamp: Long) {
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        calendar.timeInMillis = _dueTime.value
        calendar.set(Calendar.HOUR_OF_DAY, _dueHour.value)
        calendar.set(Calendar.MINUTE,_dueMinute.value)

        _dueTime.value = calendar.timeInMillis
        _dueTime.value = dateTimestamp
    }

    fun setDueClock(hour: Int, minute: Int){
        _dueHour.value = hour
        _dueMinute.value = minute
    }


    fun checkFinish() {
        _errorState.value.titleError = _taskTitle.value.isEmpty()
        _errorState.value.dueTimeError = _dueTime.value < System.currentTimeMillis()

        if (_errorState.value.titleError || _errorState.value.dueTimeError) {
            viewModelScope.launch {
                taskRepository.createTask(
                    _taskTitle.value,
                    _taskDescription.value,
                    taskAccessLevel = _accessLevel.value.level,
                    taskDueTime = _dueTime.value,
                    assignees = _assignedUser.value.map { it.id },
                )
            }
            return
        }
        _shouldScrollTop.value = true
    }

    fun clearTaskEmptyError() {
       _errorState.value.titleError = false
    }

    fun setAccessLevel(isPublic: Boolean) {
        _accessLevel.value = if (isPublic) AccessLevel.PUBLIC else AccessLevel.PRIVATE
    }

    fun clearDueTimeError() {
        _errorState.value.dueTimeError = false
    }


}
