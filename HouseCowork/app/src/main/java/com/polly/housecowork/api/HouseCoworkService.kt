package com.polly.housecowork.api

import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.House
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.UserInfo
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface HouseCoworkService {
    fun createTask(title: String, description: String, publicStatus: Int, dueTime: String, assigneeId: List<Int>): ApiResult
    fun getTask(startTime:String, endTime: String): ApiResult
    fun updateTask(title: String, description: String, publicStatus: Int): ApiResult
    fun deleteTask(taskId: Int): ApiResult

    companion object{
        private const val BASE_URL = "https://api.housecowork.com"
        fun create(): HouseCoworkService {

//            val client = OkHttpClient
//                .Builder()
//                .build()
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .build()
//                .create(HouseCoworkService::class.java)

            return MockHouseCoworkService()
        }
    }
}

class MockHouseCoworkService : HouseCoworkService {
    private val mockUserInfo = UserInfo(1, "Mock User", "Mock Nickname", "Mock Avatar", "Mock Bank Account")
    private val mockHouse = House(1, "Mock House", "Mock House Description", listOf("Mock Rule"), listOf(mockUserInfo))

    override fun createTask(title: String, description: String, publicStatus: Int, dueTime: String, assigneeId: List<Int>): ApiResult {
        val mockTask = Task(
            1,
            mockUserInfo,
            title,
            description,
            publicStatus,
            1,
            dueTime,
            listOf(mockUserInfo),
            mockHouse
        )
        return ApiResult.Success(mockTask)
    }

    override fun getTask(startTime: String, endTime: String): ApiResult {
        val mockTask = Task(
            1,
            mockUserInfo,
            "Mock Task",
            "Mock Task Description",
            1,
            1,
            "2021-10-10",
            listOf(mockUserInfo),
            mockHouse
        )
        return ApiResult.Success(
            List(10) {
                mockTask
            }
        )
    }

    override fun updateTask(title: String, description: String, publicStatus: Int): ApiResult {
        val mockTask = Task(
            1,
            mockUserInfo,
            title,
            description,
            publicStatus,
            1,
            "2021-10-10",
            listOf(mockUserInfo),
            mockHouse
        )
        return ApiResult.Success(mockTask)
    }

    override fun deleteTask(taskId: Int): ApiResult {
        return ApiResult.Success(Unit)
    }



}


