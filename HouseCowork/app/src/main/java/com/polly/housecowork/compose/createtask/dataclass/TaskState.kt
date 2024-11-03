package com.polly.housecowork.compose.createtask.dataclass

import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.ui.utils.AccessLevel
import java.util.Calendar

data class TaskState(
    var title: String = "",
    var description: String = "",
    var accessLevel: AccessLevel = AccessLevel.PUBLIC,
    var assignedUser: MutableList<ProfileInfo> = mutableListOf(),
    var selectableUsers: List<String> = emptyList(),
    var dueTime: Long = System.currentTimeMillis(),
    var dueHour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    var dueMinute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    val errors: ErrorState = ErrorState(),
    val shouldScrollTop: Boolean = false
)

