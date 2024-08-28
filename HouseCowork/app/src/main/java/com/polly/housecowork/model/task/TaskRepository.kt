package com.polly.housecowork.model.task

import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.ui.utils.AssigneeStatusType
import kotlinx.coroutines.flow.Flow


interface TaskRepository {
    suspend fun syncUpTasks()
    suspend fun getAssignedTasks(assigneeStatusId: Int, assigneeStatusType: Int, fetchRemote: Boolean = false): Flow<List<TaskDto>>
    suspend fun getOwnedTasks(fetchRemote: Boolean = false): Flow<List<TaskDto>>
    suspend fun createTask(task: TaskDto)
    suspend fun reviseTask(task: TaskDto)
    suspend fun deleteTaskById(taskId: Int)
    suspend fun deleteAllTasks()
}