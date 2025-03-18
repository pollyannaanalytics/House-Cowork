package com.polly.housecowork.model.task

import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.local.TaskDao
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AssigneeStatusType
import javax.inject.Inject

class TaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao) {


    suspend fun getTasksByAssigneeId(
        assigneeId: Int,
        assigneeStatusType: Int
    ): Result<List<TaskDto>> {
        try {
            val tasks = taskDao.getTaskByAssigneeId(assigneeId, assigneeStatusType)
            return Result.success(tasks)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

   suspend fun insertCreatedTask(
        taskDto: TaskDto
    ): Result<TaskDto> {
        try {
            taskDao.insertTask(taskDto)
            return Result.success(taskDto)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

     suspend fun updateTask(task: TaskDto): Result<TaskDto> {
        try {
            taskDao.updateTask(task)
            return Result.success(task)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

     suspend fun deleteTask(taskId: Int): Result<Unit> {
        try {
            taskDao.deleteTask(taskId)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}