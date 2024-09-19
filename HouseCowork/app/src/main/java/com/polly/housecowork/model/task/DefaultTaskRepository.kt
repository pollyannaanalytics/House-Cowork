package com.polly.housecowork.model.task

import com.polly.housecowork.data.local.TaskDao
import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskLocalDataSource: TaskDao,
    private val taskRemoteDataSource: TaskRemoteDataSource
) {
    suspend fun syncUpTasks() {

    }

    suspend fun getAssignedTasks(
        assigneeStatusId: Int,
        assigneeStatusType: Int,
        fetchRemote: Boolean
    ): Flow<List<TaskDto>> = flow {
        var taskDtos: List<TaskDto> = emptyList()
        if (!fetchRemote) {
            emit(taskLocalDataSource.getTaskByAssigneeId(assigneeStatusId, assigneeStatusType))
            return@flow
        }
        taskRemoteDataSource.getTasksByAssigneeId(assigneeStatusId, assigneeStatusType)
            .collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        taskDtos = result.data
                        emit(taskDtos)
                        taskLocalDataSource.upsertAllTasks(taskDtos)
                    }

                    is ApiResult.Error -> {
                        emit(
                            taskLocalDataSource.getTaskByAssigneeId(
                                assigneeStatusId,
                                assigneeStatusType
                            )
                        )
                    }

                    else -> {}
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun createTask(
        taskTitle: String,
        taskDescription: String,
        taskAccessLevel: Int,
        taskDueTime: Long,
        assignees: List<Int>
    ) {
        withContext(Dispatchers.IO) {
            val fetchResult = taskRemoteDataSource.createTask(
                taskTitle,
                taskDescription,
                taskAccessLevel,
                taskDueTime,
                assignees
            )
            if (fetchResult is ApiResult.Success) {
                taskLocalDataSource.updateTask(fetchResult.data)
            }
        }
    }

    suspend fun reviseTask(task: TaskDto) {
        TODO("Not yet implemented")
    }

    suspend fun deleteTaskById(taskId: Int) {
        TODO("Not yet implemented")
    }

    suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    suspend fun getOwnedTasks(fetchRemote: Boolean): Flow<List<TaskDto>> {
        TODO("Not yet implemented")
    }
}
