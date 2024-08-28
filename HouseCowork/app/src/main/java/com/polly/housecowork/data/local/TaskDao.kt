package com.polly.housecowork.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.polly.housecowork.dataclass.TaskDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<TaskDto>

    @Query("SELECT * FROM task WHERE ownerId = :userId")
    suspend fun getTaskByOwnerId(userId: Int): TaskDto

    @Query("SELECT * FROM task INNER JOIN assigneestatus ON assigneeStatusId = assigneeStatus.assigneeId WHERE assigneeStatusId = :assigneeStatus AND assigneeStatus.status = :assigneeStatusType")
    suspend fun getTaskByAssigneeId(assigneeStatus: Int, assigneeStatusType: Int): List<TaskDto>


    @Query("SELECT * FROM task WHERE taskStatus = :taskStatus")
    suspend fun getTasksByStatus(taskStatus: Int): List<TaskDto>

    @Insert
    suspend fun upsertAllTasks(tasks: List<TaskDto>)

    @Update
    suspend fun updateTask(task: TaskDto)

    @Query("DELETE FROM task")
    suspend fun deleteTask(taskId: Int)
}