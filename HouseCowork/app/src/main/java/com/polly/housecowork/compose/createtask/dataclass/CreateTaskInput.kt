package com.polly.housecowork.compose.createtask.dataclass

import com.polly.housecowork.network.model.CreateTaskRequest

data class CreateTaskInput(
    val taskTitle: String,
    val taskDescription: String,
    val taskAccessLevel: Int,
    val taskDueTime: Long,
    val assignees: List<Int>
)

fun CreateTaskInput.toTaskCreateRequest(): CreateTaskRequest {
    return CreateTaskRequest(
        title = taskTitle,
        description = taskDescription,
        accessLevel = taskAccessLevel,
        dueTime = taskDueTime.toString(),
        assigneeIds = assignees
    )
}
