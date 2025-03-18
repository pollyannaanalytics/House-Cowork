package com.polly.housecowork.model.task

import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.network.TaskApiService
import com.polly.housecowork.network.model.TaskCreateRequest

import retrofit2.Response
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val apiService: TaskApiService
) {
    suspend fun getHomeTasks(houseId: Int): Response<List<Task>> {
        return apiService.getTasksBy(
            houseId = houseId
        )
    }

    suspend fun getTasksByAssigneeId(
        houseId: Int,
        assigneeId: Int,
        assigneeStatusType: Int
    ): Response<List<Task>> {
        return apiService.getTasksBy(
            assignId = assigneeId,
            houseId = houseId,
            assigneeStatus = assigneeStatusType
        )
    }

    suspend fun createTask(
        houseId: Int,
        taskRequest: TaskCreateRequest
    ): Response<Task> {
        return apiService.createTask(
            houseId,
            taskRequest
        )
    }

    suspend fun updateTask(
        taskId: Int,
        title: String,
        description: String,
        accessLevel: Int,
        dueTime: String
    ): Response<Task> {
        return apiService.updateTask(
            taskId,
            title,
            description,
            accessLevel,
            dueTime
        )
    }

    suspend fun deleteTask(taskId: Int): Response<Unit> {
        return apiService.deleteTask(taskId)
    }
}