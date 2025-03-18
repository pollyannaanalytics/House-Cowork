package com.polly.housecowork.local

import androidx.room.Dao
import androidx.room.Insert
import com.polly.housecowork.local.model.House

@Dao
interface HouseDao {
    @Insert
    suspend fun insertHouse(house: House)
}