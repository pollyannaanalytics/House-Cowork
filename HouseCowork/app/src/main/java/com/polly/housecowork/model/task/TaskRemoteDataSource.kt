package com.polly.housecowork.model.task

import com.polly.housecowork.data.network.TaskApiService
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.dataclass.TaskInput
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val apiService: TaskApiService
){

  suspend fun getTasksByAssigneeId(
        assigneeId: Int,
        assigneeStatusType: Int
    ): Result<List<TaskDto>> {
        return apiService.getTasksBy(assignId = assigneeId, assigneeStatus = assigneeStatusType)
    }

     suspend fun createTask(taskInput: TaskInput): Result<TaskDto> {
        return apiService.createTask(taskInput)
    }

    suspend fun updateTask(task: TaskDto): Result<TaskDto> {
        return apiService.updateTask(task.title, task.description, task.accessLevel)
    }

    suspend fun deleteTask(taskId: Int): Result<Unit> {
       return apiService.deleteTask(taskId)
    }


    companion object {
        private const val TAG = "TaskRemoteDataSource"
        private const val INVALID_TASK_ID = -1
        private const val INVALID_TASK_TIME = 0L
    }

}