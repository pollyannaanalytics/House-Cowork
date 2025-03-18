package com.polly.housecowork.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.polly.housecowork.local.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<Task>

    @Query("SELECT * FROM task WHERE owner_id = :userId")
    suspend fun getTaskByOwnerId(userId: Int): Task

    @Query("""
        SELECT t.* FROM task t
        INNER JOIN task_assignee ta ON t.id = ta.task_id
        WHERE ta.assignee_id = :assigneeStatus AND ta.status = :assigneeStatusType
    """)
    suspend fun getTaskByAssigneeId(assigneeStatus: Int, assigneeStatusType: Int): List<Task>

    @Query("SELECT * FROM task WHERE status = :taskStatus")
    suspend fun getTasksByStatus(taskStatus: Int): List<Task>

    @Insert
    suspend fun upsertAllTasks(tasks: List<Task>)

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}