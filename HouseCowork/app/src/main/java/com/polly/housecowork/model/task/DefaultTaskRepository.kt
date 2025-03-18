package com.polly.housecowork.model.task

import com.polly.housecowork.network.ConnectionUtils
import com.polly.housecowork.network.model.TaskResponse
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AssigneeStatusType
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource,
    private val connectionUtils: ConnectionUtils,
    private val prefLicense: PrefsLicense
) {
    suspend fun getHomeTasks(): Result<List<TaskResponse>> {
        if (!connectionUtils.isNetworkAvailable()) {
            return localDataSource.getTasksByAssigneeId(
                prefLicense.userId,
                AssigneeStatusType.ACCEPTED.level
            )
        }

        val response = remoteDataSource.getHomeTasks(prefLicense.houseId)

        if (response.isSuccessful) {
            val tasks = response.body() ?: emptyList()
            localDataSource.updateTask(tasks)
            return Result.success(tasks)
        } else {
            return Result.failure(Exception("Failed to get tasks"))
        }
    }

}
