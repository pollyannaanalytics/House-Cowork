package com.polly.housecowork.network

import com.polly.housecowork.network.model.CreateTaskRequest
import com.polly.housecowork.network.model.TaskResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {

    @POST("tasks/house/{houseId}")
    suspend fun createTask(
        @Path("houseId") houseId: Int,
        taskRequest: CreateTaskRequest
    ): Response<TaskResponse>

    @GET("tasks/house/{houseId}")
    suspend fun getTasksBy(
        @Path("houseId") houseId: Int,
        ownerId: Int? = null,
        assignId: Int? = null,
        startTime: Long? = null,
        taskStatus: Int? = null,
        assigneeStatus: Int? = null
    ): Response<List<TaskResponse>>


    @GET("tasks/house/{houseId}/assigned")
    suspend fun getAssignedTasks(
        @Path("houseId") houseId: Int,
    ): Response<List<TaskResponse>>

    @GET("tasks/house/{houseId}/home")
    suspend fun getHomeTasks(
        @Path("houseId") houseId: Int,
    ): Response<List<TaskResponse>>

    @PATCH("tasks/{taskId}")
    suspend fun updateTask(
        @Path("taskId") taskId: Int,
        title: String,
        description: String,
        accessLevel: Int,
        dueTime: String
    ): Response<TaskResponse>

    @DELETE("tasks/{taskId}")
    suspend fun deleteTask(@Path("taskId") taskId: Int): Response<Unit>
}
