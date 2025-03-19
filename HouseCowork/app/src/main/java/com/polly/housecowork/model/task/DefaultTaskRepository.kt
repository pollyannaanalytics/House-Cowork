package com.polly.housecowork.model.task

import com.polly.housecowork.compose.createtask.dataclass.CreateTaskInput
import com.polly.housecowork.compose.createtask.dataclass.toTaskCreateRequest
import com.polly.housecowork.dataclass.TaskDomain
import com.polly.housecowork.network.ConnectionUtils
import com.polly.housecowork.prefs.PrefsLicense
import javax.inject.Inject


class DefaultTaskRepository @Inject constructor(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource,
    private val connectionUtils: ConnectionUtils,
    private val prefLicense: PrefsLicense
) {
   suspend fun getHomeTasks(): List<TaskDomain> {
        if (!connectionUtils.isNetworkAvailable()) {
            return localDataSource.getHomeTasks()
        }

        val response = remoteDataSource.getHomeTasks(prefLicense.houseId)

        if (response.isSuccessful) {
            val tasks = response.body() ?: emptyList()
            localDataSource.updateTasks(tasks)
            return tasks
        } else {
            return emptyList()
        }
    }

    suspend fun createTask(
        createTaskInput: CreateTaskInput
    ): Result<TaskDomain> {
        if (!connectionUtils.isNetworkAvailable()) {
            return Result.failure(Exception("No network"))
        }

        val response = remoteDataSource.createTask(prefLicense.houseId, createTaskInput.toTaskCreateRequest())

        return if (response.isSuccessful) {
            val task = response.body() ?: return Result.failure(Exception("No body"))
            localDataSource.insertCreatedTask(task)
            Result.success(task)
        } else {
            Result.failure(Exception("Error"))
        }
    }

}
