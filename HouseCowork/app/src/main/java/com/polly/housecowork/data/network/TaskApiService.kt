package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.dataclass.TaskInput
import kotlin.random.Random

interface TaskApiService {
    suspend fun createTask(
       taskInput: TaskInput
    ): Result<TaskDto>

    suspend fun getTasksBy(
        ownerId: Int? = null,
        assignId: Int? = null,
        startTime: Long? = null,
        houseId: Int? = null,
        taskStatus: Int? = null,
        assigneeStatus: Int? = null
    ): Result<List<TaskDto>>

    suspend fun updateTask(title: String, description: String, accessLevel: Int): Result<TaskDto>
    suspend fun deleteTask(taskId: Int): Result<Unit>
}

class MockTaskApiService : TaskApiService {
    private val mockTaskList: List<TaskDto> = List(5) { index ->
        TaskDto(
            index,                          // 使用遞增的 index 作為唯一 taskId
            Random.nextInt(1000000),         // 唯一 userId
            "Mock Task $index",              // 唯一任務名稱
            "Mock Task Description $index",  // 唯一描述
            1,                               // 固定狀態
            1,                               // 固定優先順序
            "2023-07-28T14:30:00Z",          // 固定期限
            1,                               // 固定任務類型
            1,                               // 固定任務分類
            System.currentTimeMillis(),      // 當前時間作為創建時間
            System.currentTimeMillis()       // 當前時間作為更新時間
        )
    }



    override suspend fun createTask(
        taskInput: TaskInput
    ): Result<TaskDto> {
        return  Result.success(mockTaskList.first())
    }

    override suspend fun getTasksBy(
        ownerId: Int?,
        assignId: Int?,
        startTime: Long?,
        houseId: Int?,
        taskStatus: Int?,
        assigneeStatus: Int?
    ): Result<List<TaskDto>> {
        return Result.success(mockTaskList)
    }

    override suspend fun updateTask(
        title: String,
        description: String,
        accessLevel: Int
    ): Result<TaskDto> {
        return Result.success(mockTaskList.first())
    }

    override suspend fun deleteTask(taskId: Int): Result<Nothing> {
        return Result.success(
            Nothing::class.java.newInstance()
        )
    }

}


