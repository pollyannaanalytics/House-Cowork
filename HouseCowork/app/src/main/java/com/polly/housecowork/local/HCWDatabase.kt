package com.polly.housecowork.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.polly.housecowork.local.model.Assignee
import com.polly.housecowork.local.model.House
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.local.model.HouseMemberTypeConverter
import com.polly.housecowork.local.model.Task
import com.polly.housecowork.model.profile.ProfileDao

@Database(entities = [Profile::class,  House::class, Assignee::class, Task::class], version = 1)
@TypeConverters(HouseMemberTypeConverter::class)
abstract class HCWDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun profileDao(): ProfileDao
    abstract fun houseDao(): HouseDao

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