package com.polly.housecowork.model.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.polly.housecowork.dataclass.ProfileInfo

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_info WHERE id = :profileId")
    fun getProfileById(profileId: Int): ProfileInfo

    @Query("SELECT * FROM profile_info")
    fun getAllProfiles(): List<ProfileInfo>

    @Insert
    fun insertProfile(profile: ProfileInfo)

    @Update
    fun updateProfile(profile: ProfileInfo)

    @Query("DELETE FROM profile_info WHERE id = :profileId")
    fun deleteProfileById(profileId: Int)
}