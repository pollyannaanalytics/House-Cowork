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

    @Query("SELECT * FROM task INNER JOIN assignee_status ON assignee_status_id = assignee_status.id WHERE assignee_status_id = :assigneeStatus AND assignee_status.status = :assigneeStatusType")
    suspend fun getTaskByAssigneeId(assigneeStatus: Int, assigneeStatusType: Int): List<TaskDto>

    @Query("SELECT * FROM task WHERE task_status = :taskStatus")
    suspend fun getTasksByStatus(taskStatus: Int): List<TaskDto>

    @Insert
    suspend fun upsertAllTasks(tasks: List<TaskDto>)

    @Insert
    suspend fun insertTask(task: TaskDto)

    @Update
    suspend fun updateTask(task: TaskDto)

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}