package com.polly.housecowork.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "task")
data class Task(
    val id: Int,
    @ColumnInfo(name = "owner_id")
    val ownerId: Int,
    val title: String,
    val description: String,

    @ColumnInfo(name = "access_level")
    val accessLevel: Int,
    val status: Int,

    @ColumnInfo(name = "due_time")
    val dueTime: String,
)


data class Assignee(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "task_id")
    val taskId: Int,

    @ColumnInfo(name = "assignee_id")
    val assigneeId: Int,

    val status: Int
)

@Entity(
    tableName = "task_assignee",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["id"],
            childColumns = ["task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskAssignee(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "task_id")
    val taskId: Int,

    @ColumnInfo(name = "assignee_id")
    val assigneeId: Int,
    val status: Int
)


data class TaskWithAssignees(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "task_id",
        entity = Assignee::class
    )
    val assignees: List<Assignee>
)