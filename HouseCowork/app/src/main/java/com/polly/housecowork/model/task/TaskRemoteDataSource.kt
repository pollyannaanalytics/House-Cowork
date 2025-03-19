package com.polly.housecowork.model.task

import com.polly.housecowork.dataclass.TaskDomain
import com.polly.housecowork.network.TaskApiService
import com.polly.housecowork.network.model.CreateTaskRequest
import com.polly.housecowork.network.model.TaskResponse

import retrofit2.Response
import javax.inject.Inject

interface TaskRemoteDataSource {
    suspend fun getHomeTasks(houseId: Int): Response<List<TaskDomain>>
    suspend fun getAssignedTasks(houseId: Int): Response<List<TaskDomain>>
    suspend fun getTasksBy(
        houseId: Int,
        ownerId: Int? = null,
        assignId: Int? = null,
        startTime: Long? = null,
        taskStatus: Int? = null,
        assigneeStatus: Int? = null
    ): Response<List<TaskDomain>>

    suspend fun createTask(
        houseId: Int,
        taskRequest: CreateTaskRequest
    ): Response<TaskDomain>

    suspend fun updateTask(
        taskId: Int,
        title: String,
        description: String,
        accessLevel: Int,
        dueTime: String
    ): Response<TaskDomain>

    suspend fun deleteTask(taskId: Int): Response<Unit>
}

class DefaultTaskRemoteDataSource @Inject constructor(
    private val apiService: TaskApiService
) : TaskRemoteDataSource {
    override suspend fun getHomeTasks(houseId: Int): Response<List<TaskDomain>> {
        val response = apiService.getHomeTasks(
            houseId = houseId
        )
        return handleResponses(response)
    }

    override suspend fun getAssignedTasks(houseId: Int): Response<List<TaskDomain>> {
        val response = apiService.getAssignedTasks(
            houseId
        )
        return handleResponses(response)
    }

    override suspend fun getTasksBy(
        houseId: Int,
        ownerId: Int?,
        assignId: Int?,
        startTime: Long?,
        taskStatus: Int?,
        assigneeStatus: Int?
    ): Response<List<TaskDomain>> {
        val response = apiService.getTasksBy(
            houseId,
            ownerId,
            assignId,
            startTime,
            taskStatus,
            assigneeStatus
        )

        return handleResponses(response)
    }

    override suspend fun createTask(
        houseId: Int,
        taskRequest: CreateTaskRequest
    ): Response<TaskDomain> {
        val response = apiService.createTask(
            houseId,
            taskRequest
        )

        return handleResponse(response)

    }

    override suspend fun updateTask(
        taskId: Int,
        title: String,
        description: String,
        accessLevel: Int,
        dueTime: String
    ): Response<TaskDomain> {
        val response = apiService.updateTask(taskId, title, description, accessLevel, dueTime)
        return handleResponse(response)
    }

    override suspend fun deleteTask(taskId: Int): Response<Unit> {
        return apiService.deleteTask(taskId)
    }

    private fun TaskResponse.toDomain(): TaskDomain {
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

    private fun handleResponses(response: Response<List<TaskResponse>>): Response<List<TaskDomain>> {
        return if (response.isSuccessful) {
            val tasks = response.body() ?: emptyList()
            Response.success(tasks.map { it.toDomain() })
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    private fun handleResponse(response: Response<TaskResponse>): Response<TaskDomain> {
        return if (response.isSuccessful) {
            val task =
                response.body() ?: return Response.error(response.code(), response.errorBody()!!)
            Response.success(task.toDomain())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}