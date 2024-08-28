package com.polly.housecowork.dataclass

import androidx.room.Entity

@Entity(tableName = "profile_info")
data class ProfileInfo(
    val id: Int,
    val name: String,
    val nickName: String,
    val avatar: String,
    val bankAccount: String,
)

