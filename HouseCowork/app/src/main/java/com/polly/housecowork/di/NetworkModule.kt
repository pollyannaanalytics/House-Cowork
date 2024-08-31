package com.polly.housecowork.di

import com.polly.housecowork.data.network.HCWApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHouseCoworkService(): HCWApiService {
        return HCWApiService.create()
    }
}