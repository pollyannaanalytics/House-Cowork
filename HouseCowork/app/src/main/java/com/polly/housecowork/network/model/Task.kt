package com.polly.housecowork.network.model

import android.os.Parcelable
import com.polly.housecowork.dataclass.Assignee
import com.polly.housecowork.dataclass.TaskDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateTaskRequest(
    val title: String,
    val description: String,
    val accessLevel: Int,
    val dueTime: String,
    val assigneeIds: List<Int>
): Parcelable


@Parcelize
data class TaskResponse(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val accessLevel: Int,
    val status: Int,
    val dueTime: String,
    val assignees: List<Assignee>
) : Parcelable

fun TaskResponse.toDomain(): TaskDomain {
    return TaskDomain(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        accessLevel = accessLevel,
        status = status,
        dueTime = dueTime,
        assignees = assignees
    )
}

