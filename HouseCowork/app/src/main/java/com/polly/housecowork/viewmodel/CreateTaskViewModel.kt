package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import com.polly.housecowork.dataclass.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor() : ViewModel() {

    data class ErrorState(
        var titleError: Boolean = false,
        var dueTimeError: Boolean = false
    )

    private val _taskTitle: MutableStateFlow<String> = MutableStateFlow("")

    private var _assignedUser: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val assignedUser = _assignedUser.asStateFlow()

    private var _allUsers: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val allUsers = _allUsers.asStateFlow()

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
    }

    private fun setInitialDueTime(){
        val simpleHourFormat = SimpleDateFormat("HH")
        val currentTime = System.currentTimeMillis()
        val currentDate = Date(currentTime)
        _dueHour.value = simpleHourFormat.format(currentDate).toInt()
        _dueMinute.value = (currentTime / 1000 / 60 % 60).toInt()
    }

    fun setTaskTitle(title: String) {
        _taskTitle.value = title
    }
    fun setAssignedUser(userName: String) {
//        _assignedUser.value = user
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
            // save task
            return
        }
        _shouldScrollTop.value = true
    }

    fun clearTaskEmptyError() {
       _errorState.value.titleError = false
    }

    fun clearDueTimeError() {
        _errorState.value.dueTimeError = false
    }

    fun checkDueTime(){
        _errorState.value.dueTimeError = _dueTime.value < System.currentTimeMillis()
    }

}