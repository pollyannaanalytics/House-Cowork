package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity
data class UserInfo(
    @PrimaryKey
    val id: Int = 0,
    val uid: UUID = UUID.randomUUID(),
    val name: String,
    val nickName: String,
    val avatar: String,
    @ColumnInfo(name = "bank_account")
    val bankAccount: String,
)




