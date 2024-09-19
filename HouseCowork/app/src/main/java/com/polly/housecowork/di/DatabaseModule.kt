package com.polly.housecowork.di

import android.content.Context
import com.polly.housecowork.data.local.HCWDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): HCWDatabase {
        return HCWDatabase.getInstance(context)
    }

    @Provides
    fun provideProfileDao(database: HCWDatabase) = database.profileDao()

    @Provides
    fun provideTaskDao(database: HCWDatabase) = database.taskDao()


}