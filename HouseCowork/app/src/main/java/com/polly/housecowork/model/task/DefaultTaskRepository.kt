package com.polly.housecowork.model.task

import com.polly.housecowork.data.local.TaskDao
import com.polly.housecowork.dataclass.Result
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.prefs.PrefsMain
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskLocalDataSource: TaskDao,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val prefsMain: PrefsMain
) : TaskRepository {
    override suspend fun syncUpTasks() {
        val result = taskRemoteDataSource.getAllTasks()
        if (result is Result.Success) {
            val tasks = result.data as List<TaskDto>
            taskLocalDataSource.upsertAllTasks(tasks.filter {
                it.updatedTime > prefsMain.lastTaskUpdateTime
            })
            prefsMain.lastTaskUpdateTime = System.currentTimeMillis()
        }
    }

    override suspend fun getAssignedTasks(
        assigneeStatusId: Int,
        assigneeStatusType: Int,
        fetchRemote: Boolean
    ): Flow<List<TaskDto>> = flow {
        var taskDtos: List<TaskDto> = emptyList()
        if (!fetchRemote) {
            emit(taskLocalDataSource.getTaskByAssigneeId(assigneeStatusId, assigneeStatusType))
            return@flow
        }
        val result = taskRemoteDataSource.getTasksByAssigneeId(assigneeStatusId, assigneeStatusType)
        if (result is Result.Success) {
            taskDtos = result.data as List<TaskDto>
            emit(taskDtos)
        }
        taskLocalDataSource.upsertAllTasks(taskDtos)
    }.flowOn(Dispatchers.IO)

    override suspend fun createTask(
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
            if (fetchResult is Result.Success){
                taskLocalDataSource.updateTask(fetchResult.data as TaskDto)
            }
        }
    }

    override suspend fun reviseTask(task: TaskDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskById(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun getOwnedTasks(fetchRemote: Boolean):Flow<List<TaskDto>>{
        TODO("Not yet implemented")
    }
}
