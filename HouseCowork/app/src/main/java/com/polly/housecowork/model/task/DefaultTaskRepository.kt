package com.polly.housecowork.model.task

import android.util.Log
import com.polly.housecowork.dataclass.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource
) {

    suspend fun getAssignedTasks(
        assigneeStatusId: Int,
        assigneeStatusType: Int,
        fetchRemote: Boolean
    ): Flow<Result<List<TaskDto>>> = flow {
        if (!fetchRemote) {
            emit(taskLocalDataSource.getTasksByAssigneeId(assigneeStatusId, assigneeStatusType))
            return@flow
        }
        val fetchResult = taskRemoteDataSource.getTasksByAssigneeId(assigneeStatusId, assigneeStatusType)
        fetchResult.fold(
            onSuccess = { tasks ->
                emit(Result.success(tasks))
                tasks.forEach { taskLocalDataSource.updateTask(it) }
            },
            onFailure = { error ->
                Log.e("DefaultTaskRepository", "getAssignedTasks: $error")
                emit(Result.failure(error))
                coroutineScope {
                    emit(taskLocalDataSource.getTasksByAssigneeId(assigneeStatusId, assigneeStatusType))
                }
            }
        )
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
            fetchResult.fold(
                onSuccess = { taskDto ->
                    taskLocalDataSource.insertCreatedTask(taskDto)
                },
                onFailure = { error ->
                    return@withContext Result.failure(error)
                }
            )
            return@withContext fetchResult
        }
    }

    suspend fun reviseTask(task: TaskDto) = withContext(Dispatchers.IO) {
        val fetchResult = taskRemoteDataSource.updateTask(task)
        fetchResult.fold(
            onSuccess = { taskDto ->
                taskLocalDataSource.updateTask(taskDto)
            },
            onFailure = { error ->
                return@withContext Result.failure(error)
            }
        )
        return@withContext fetchResult

    }

    suspend fun deleteTaskById(taskId: Int) = withContext(Dispatchers.IO) {
        val fetchResult = taskRemoteDataSource.deleteTask(taskId)
        fetchResult.fold(
            onSuccess = {
                taskLocalDataSource.deleteTask(taskId)
            },
            onFailure = { error ->
                return@withContext Result.failure(error)
            }
        )
        return@withContext fetchResult
    }
}
