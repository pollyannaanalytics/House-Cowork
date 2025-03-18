package com.polly.housecowork.model.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.polly.housecowork.local.model.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfileById(profileId: Int): Profile

    @Query("SELECT * FROM profile")
    fun getAllProfiles(): List<Profile>

    @Insert
    fun insertProfile(profile: Profile)

    @Update
    fun updateProfile(profile: Profile)

    @Insert
    fun upsertAllProfiles(profiles: List<Profile>)

    @Query("DELETE FROM profile WHERE id = :profileId")
    fun deleteProfileById(profileId: Int)
}