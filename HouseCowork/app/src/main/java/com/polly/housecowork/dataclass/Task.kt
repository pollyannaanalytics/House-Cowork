package com.polly.housecowork.dataclass


data class Task (
    val id: Long = 0,
    val taskName: String,
    val taskAssigneeId: Long,
    val isPublic : Boolean,
    val taskReporterId: Long,
    val taskDescription: String,
    val deadline: Long,
    val taskStatus: TaskStatus,
    val cost: Int,
    val isClosed: Boolean
)

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}