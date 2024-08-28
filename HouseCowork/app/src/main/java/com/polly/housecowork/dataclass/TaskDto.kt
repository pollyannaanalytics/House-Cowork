package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.polly.housecowork.ui.utils.AccessLevel
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
    var taskStatus: Int,
    val dueTime: String,
    val assigneeStatusId: Int,
    val houseId: Int,
    val createdTime: Long,
    val updatedTime: Long
)

@Entity
data class AssigneeStatus(
    @PrimaryKey
    val id: Int,
    val assigneeId: Int,
    val taskId: Int,
    val status: Int
)


data class Task(
    val id: Int,
    val owner: ProfileInfo,
    val title: String,
    val description: String,
    val accessLevel: AccessLevel,
    var taskStatus: TaskStatus,
    val dueTime: String,
    val assigneeStatus: List<AssigneeStatus>,
    val createdTime: Long,
    val updatedTime: Long
)



