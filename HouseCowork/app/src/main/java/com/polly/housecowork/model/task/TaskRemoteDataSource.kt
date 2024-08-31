package com.polly.housecowork.model.task

import com.polly.housecowork.dataclass.Result
import com.polly.housecowork.data.network.HCWApiService
import com.polly.housecowork.dataclass.TaskDto
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val apiService : HCWApiService
){
    suspend fun getAllTasks(): Result<List<TaskDto>> {
        return try {
            val result = apiService.getAllTasks()
            if (result is Result.Success<List<TaskDto>>) {
                Result.Success(result.data)
            } else {
                Result.Error(Exception("Failed to fetch tasks from API"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getTasksByAssigneeId(assigneeId: Int, assigneeStatusType: Int): Result<List<TaskDto>> {
        return try {
            val result = apiService.getTasksByUserId(assigneeId, assigneeStatusType)
            if (result is Result.Success<List<TaskDto>>) {
                Result.Success(result.data)
            } else {
                Result.Error(Exception("Failed to fetch task by id: ${assigneeId}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getLastTaskUpdateTime(): Long {
        val result = apiService.updateLastEditTime()
        return if (result is Result.Success) result.data as Long else INVALID_TASK_TIME
    }



    suspend fun createTask(
        taskTitle: String,
        taskDescription: String,
        taskAccessLevel: Int,
        taskDueTime: Long,
        assignees: List<Int>
        ): Result<TaskDto> {
        return apiService.createTask(taskTitle, taskDescription, taskAccessLevel, taskDueTime, assignees)
    }

    suspend fun updateTask(task: TaskDto): Result<Nothing> {
        val result = apiService.updateTask(task.title, task.description, task.accessLevel)
        return if (result is Result.Success) {
            Result.Success(Unit)
        } else {
            Result.Error(
                Result.Error.Reason.SearchFailed(e = Exception("Network: Failed to update task"))
            )
        }
    }

    suspend fun deleteTask(taskId: Int): Result<Nothing> {
        val result = apiService.deleteTask(taskId)
        return if (result is Result.Success) {
            Result.Success(Unit)
        } else {
            Result.Error(
                Result.Error.Reason.SearchFailed(e = Exception("Network: Failed to delete task by id: $taskId"))
            )
        }
    }


    companion object{
        private const val TAG = "TaskRemoteDataSource"
        private const val INVALID_TASK_ID = -1
        private const val INVALID_TASK_TIME = 0L
    }

}