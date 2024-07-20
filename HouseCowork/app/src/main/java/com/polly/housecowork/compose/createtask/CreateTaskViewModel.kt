package com.polly.housecowork.compose.createtask

import androidx.lifecycle.ViewModel
import com.polly.housecowork.dataclass.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor() : ViewModel() {
    private val _taskTitle: MutableStateFlow<String> = MutableStateFlow("")
    val taskTitle = _taskTitle.asStateFlow()

    private var _assignedUser: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val assignedUser = _assignedUser.asStateFlow()

    private var _allUsers: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val allUsers = _allUsers.asStateFlow()

    private var _dueTime: MutableStateFlow<Long> = MutableStateFlow(0L)
    val dueTime = _dueTime.asStateFlow()

    fun setTaskTitle(title: String) {
        _taskTitle.value = title
    }
    fun setAssignedUser(userName: String) {
//        _assignedUser.value = user
    }

    fun setDueTime(time: Long) {
        _dueTime.value = time
    }

    fun finishCreateTask() {

    }
}
