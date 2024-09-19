package com.polly.housecowork.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.dataclass.House
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.profile.ProfileDao

@Database(entities = [ProfileInfo::class, TaskDto::class, House::class, AssigneeStatus::class], version = 1)
abstract class HCWDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun profileDao(): ProfileDao

    companion object{
        @Volatile
        private var INSTANCE: HCWDatabase? = null

        fun getInstance(context: Context): HCWDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HCWDatabase::class.java,
                    "house_cowork_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}