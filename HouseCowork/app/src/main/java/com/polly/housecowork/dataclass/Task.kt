package com.polly.housecowork.dataclass

import com.polly.housecowork.compose.createtask.dataclass.ErrorState
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.TaskStatus
import java.util.Calendar

data class CreateTaskState(
    var title: String = "",
    var description: String = "",
    var accessLevel: AccessLevel = AccessLevel.PUBLIC,
    var assignedUser: MutableList<Profile> = mutableListOf(),
    var selectableUsers: List<String> = emptyList(),
    var dueTime: Long = System.currentTimeMillis(),
    var dueHour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    var dueMinute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    val errors: ErrorState = ErrorState(),
    val shouldScrollTop: Boolean = false
)

data class TaskState(
    val id: Int,
    val owner: Profile,
    val title: String,
    val description: String,
    val accessLevel: AccessLevel,
    val status: TaskStatus,
    val dueTime: String,
    val dueDate: String,
    val assignees: List<Assignee>
)


data class TaskDomain(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val accessLevel: Int,
    val status: Int,
    val dueTime: String,
    val assignees: List<Assignee>
)


