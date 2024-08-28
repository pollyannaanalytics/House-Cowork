package com.polly.housecowork.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.polly.housecowork.dataclass.House
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.profile.ProfileDao

@Database(entities = [ProfileInfo::class, TaskDto::class, House::class], version = 1)
abstract class HCWDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun profileDao(): ProfileDao
}