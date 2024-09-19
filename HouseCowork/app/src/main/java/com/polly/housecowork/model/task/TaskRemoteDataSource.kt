package com.polly.housecowork.model.task

import com.polly.housecowork.data.network.TaskApiService
import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.prefs.PrefsLicense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val apiService : TaskApiService,
    private val license: PrefsLicense
){

    suspend fun getTasksByAssigneeId(assigneeId: Int, assigneeStatusType: Int): Flow<ApiResult<List<TaskDto>>> = flow {
       emit(ApiResult.Loading)
        try {
            emit(ApiResult.Success(apiService.getTasksBy(assigneeId, assigneeStatusType)))
        }catch (e: Exception){
            emit(ApiResult.Error(e))
        }
    }


    suspend fun createTask(
        taskTitle: String,
        taskDescription: String,
        taskAccessLevel: Int,
        taskDueTime: Long,
        assignees: List<Int>
        ): ApiResult<TaskDto> {
        return try {
            ApiResult.Success(apiService.createTask(taskTitle, taskDescription, taskAccessLevel, taskDueTime, assignees))
        }catch (e: Exception){
            ApiResult.Error(e)
        }
    }

//    suspend fun updateTask(task: TaskDto): Result<Nothing> {
//        val result = apiService.updateTask(task.title, task.description, task.accessLevel)
//        return if (result is Result.Success) {
//            Result.Success(Unit)
//        } else {
//            Result.Error(
//                Result.Error.Reason.SearchFailed(e = Exception("Network: Failed to update task"))
//            )
//        }
//    }

//    suspend fun deleteTask(taskId: Int): Result<Nothing> {
//        val result = apiService.deleteTask(taskId)
//        return if (result is Result.Success) {
//            Result.Success(Unit)
//        } else {
//            Result.Error(
//                Result.Error.Reason.SearchFailed(e = Exception("Network: Failed to delete task"))
//            )
//        }
//    }


    companion object{
        private const val TAG = "TaskRemoteDataSource"
        private const val INVALID_TASK_ID = -1
        private const val INVALID_TASK_TIME = 0L
    }

}