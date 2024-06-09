package com.polly.housecowork.di

import com.polly.housecowork.api.HouseCoworkService
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
    fun provideHouseCoworkService(): HouseCoworkService {
        return HouseCoworkService.create()
    }
}