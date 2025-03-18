package com.polly.housecowork.di

import com.polly.housecowork.model.calendar.CalendarDataSource
import com.polly.housecowork.model.calendar.DefaultCalendarDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CalendarModule {

    @Provides
    fun provideCalendarDataSource(): CalendarDataSource {
        return DefaultCalendarDataSource()
    }

}