package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.TaskDto

interface TaskApiService {
    suspend fun createTask(
        taskTitle: String,
        taskDescription: String,
        taskAccessLevel: Int,
        taskDueTime: Long,
        assignees: List<Int>
    ): TaskDto

    suspend fun getTasksBy(
        ownerId: Int? = null,
        assignId: Int? = null,
        startTime: Long? = null,
        houseId: Int? = null,
        taskStatus: Int? = null,
        assigneeStatus: Int? = null
    ): List<TaskDto>

    suspend fun updateTask(title: String, description: String, accessLevel: Int)
    suspend fun deleteTask(taskId: Int)
}

class MockTaskApiService : TaskApiService {
    private val mockTaskDto = TaskDto(
        1,
        0,
        "Mock Task",
        "Mock Task Description",
        1,
        0,
        "2021-10-10",
        1,
        1,
        System.currentTimeMillis(),
        System.currentTimeMillis()
    )

    override suspend fun createTask(
        taskTitle: String,
        taskDescription: String,
        taskAccessLevel: Int,
        taskDueTime: Long,
        assignees: List<Int>
    ): TaskDto {
        return  mockTaskDto
    }

    override suspend fun getTasksBy(
        ownerId: Int?,
        assignId: Int?,
        startTime: Long?,
        houseId: Int?,
        taskStatus: Int?,
        assigneeStatus: Int?
    ): List<TaskDto> {
        return List(10) { mockTaskDto }
    }

    override suspend fun updateTask(
        title: String,
        description: String,
        accessLevel: Int
    ){}

    override suspend fun deleteTask(taskId: Int){}

}


