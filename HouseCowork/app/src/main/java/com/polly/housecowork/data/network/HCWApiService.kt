package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.Result
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.utils.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface HCWApiService {
    suspend fun createTask(
        taskTitle: String,
        taskDescription: String,
        taskAccessLevel: Int,
        taskDueTime: Long,
        assignees: List<Int>): Result<TaskDto>
    suspend fun getAllTasks(): Result<List<TaskDto>>
    suspend fun getTaskById(taskId: Int): Result<TaskDto>
    suspend fun getTasksByUserId(userId: Int, assigneeStatus: Int): Result<List<TaskDto>>
    suspend fun updateTask(title: String, description: String, accessLevel: Int): Result<Nothing>
    suspend fun deleteTask(taskId: Int): Result<Nothing>
    suspend fun updateLastEditTime(): Result<Long>

    companion object {
        fun create(): HCWApiService {

//            val client = OkHttpClient
//                .Builder()
//                .build()
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .build()
//                .create(HCWApiService::class.java)

            return MockHCWApiService()
        }
    }
}

class MockHCWApiService : HCWApiService {
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
    ):  Result<TaskDto> {
        return Result.Success(List(10) { mockTaskDto })
    }

    override suspend fun getAllTasks(): Result<List<TaskDto>> {
        val mockTaskDto = TaskDto(
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
        return Result.Success(List(10) { mockTaskDto })

    }

    override suspend fun getTaskById(taskId: Int): Result<TaskDto> {

        return Result.Success(mockTaskDto)
    }

    override suspend fun getTasksByUserId(userId: Int, assigneeStatus: Int): Result<List<TaskDto>> {
        return Result.Success(List(10) { mockTaskDto })
    }

    override suspend fun updateTask(
        title: String,
        description: String,
        accessLevel: Int
    ): Result<Nothing> {
        return Result.Success(Unit)
    }

    override suspend fun deleteTask(taskId: Int): Result<Nothing> {
        return Result.Success(Unit)
    }

    override suspend fun updateLastEditTime(): Result<Long> {
        return Result.Success(System.currentTimeMillis())
    }
}


