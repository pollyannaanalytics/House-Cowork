package com.polly.housecowork.di

import android.content.Context
import com.polly.housecowork.MainApplication
import com.polly.housecowork.data.local.TaskDao
import com.polly.housecowork.data.network.AuthApiService
import com.polly.housecowork.data.network.CalendarApiService
import com.polly.housecowork.data.network.ConnectionUtils
import com.polly.housecowork.data.network.MockProfileApiService
import com.polly.housecowork.data.network.MockTaskApiService
import com.polly.housecowork.data.network.ProfileApiService
import com.polly.housecowork.data.network.TaskApiService
import com.polly.housecowork.model.task.DefaultTaskRepository
import com.polly.housecowork.model.task.TaskRemoteDataSource
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskService(retrofit: Retrofit): TaskApiService {
        return MockTaskApiService()
    }

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileApiService {
        return MockProfileApiService()
    }

    @Provides
    @Singleton
    fun provideConnectionUtils(@ApplicationContext context: Context): ConnectionUtils {
        return ConnectionUtils(context)
    }
}