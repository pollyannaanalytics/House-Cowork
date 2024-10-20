package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import java.util.UUID


@Entity(tableName = "task")
data class TaskDto(
    @PrimaryKey
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "access_level")
    val accessLevel: Int,

    @ColumnInfo(name = "task_status")
    var taskStatus: Int,
    val dueTime: String,

    @ColumnInfo(name = "assignee_status_id")
    val assigneeStatusId: Int,

    @ColumnInfo(name = "house_id")
    val houseId: Int,

    @ColumnInfo(name = "created_time")
    val createdTime: Long,

    @ColumnInfo(name = "updated_time")
    val updatedTime: Long
)

@Entity(tableName = "assignee_status")
data class AssigneeStatus(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "assignee_id")
    val assigneeId: Int,

    @ColumnInfo(name = "task_id")
    val taskId: Int,
    val status: Int
)

data class AssignedTask(
    val id: Int,
    val owner: ProfileInfo,
    val title: String,
    val description: String,
    val accessLevel: AccessLevel,
    var taskStatus: TaskStatus,
    val dueDate: String,
    val dueTime: String,
    val assigneeStatus: Int,
    val createdTime: Long,
    val updatedTime: Long
)

data class Task(
    val id: Int,
    val owner: ProfileInfo,
    val title: String,
    val description: String,
    val accessLevel: AccessLevel,
    var taskStatus: TaskStatus,
    val dueDate: String,
    val dueTime: String,
    val assigneeStatus: List<AssigneeStatus>,
    val createdTime: Long,
    val updatedTime: Long
)



