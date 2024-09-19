package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_info")
data class ProfileInfo(
    @PrimaryKey
    val id: Int,
    val name: String,

    @ColumnInfo(name = "nick_name")
    val nickName: String,
    val avatar: String,

    @ColumnInfo(name = "bank_account")
    val bankAccount: String,
    val email: String,

    @ColumnInfo(name = "update_time")
    val updateTime: Long
)

