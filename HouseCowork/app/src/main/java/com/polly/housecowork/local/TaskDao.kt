package com.polly.housecowork.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.polly.housecowork.local.model.Task
import com.polly.housecowork.local.model.TaskWithAssignees

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<TaskWithAssignees>

    @Transaction
    @Query("SELECT * FROM task WHERE owner_id = :userId")
    suspend fun getTaskByOwnerId(userId: Int): TaskWithAssignees

    @Transaction
    @Query("SELECT * FROM task WHERE status = :taskStatus")
    suspend fun getTasksByStatus(taskStatus: Int): List<TaskWithAssignees>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllTasks(tasks: List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Update
    suspend fun updateTasks(tasks: List<Task>)

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}