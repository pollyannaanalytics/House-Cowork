package com.polly.housecowork.di

import com.polly.housecowork.local.TaskDao
import com.polly.housecowork.model.task.DefaultTaskLocalDataSource
import com.polly.housecowork.model.task.DefaultTaskRemoteDataSource
import com.polly.housecowork.model.task.TaskLocalDataSource
import com.polly.housecowork.model.task.TaskRemoteDataSource
import com.polly.housecowork.network.TaskApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {
    @Provides
    fun provideTaskRemoteDataSource(
        apiService: TaskApiService
    ): TaskRemoteDataSource {
        return DefaultTaskRemoteDataSource(apiService = apiService)
    }


    @Provides
    fun provideTaskLocalDataSource(
        taskDao: TaskDao
    ): TaskLocalDataSource {
        return DefaultTaskLocalDataSource(taskDao = taskDao)
    }


}