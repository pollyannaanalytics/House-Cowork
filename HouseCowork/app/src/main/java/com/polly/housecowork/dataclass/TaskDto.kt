package com.polly.housecowork.dataclass

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.TypeConverter
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.TaskStatus
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.parcelize.Parcelize


data class Task(
    val id: Int,
    val owner: Profile,
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


data class TaskInput(
    val taskTitle: String,
    val taskDescription: String,
    val taskAccessLevel: Int,
    val taskDueTime: Long,
    val assignees: List<Int>
)


