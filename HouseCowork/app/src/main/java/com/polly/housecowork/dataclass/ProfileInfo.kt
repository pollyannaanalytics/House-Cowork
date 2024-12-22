package com.polly.housecowork.dataclass

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileRequest(
    val name: String,
    val nickName: String,
    val avatar: String,
    val bankAccount: String
): Parcelable

@Parcelize
data class ProfileInfoResponse (
    val user: User
): Parcelable

@Parcelize
@Entity(tableName = "profile_info")
data class ProfileInfo(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val email: String,
    val bio: String = "",

    @ColumnInfo(name = "nick_name")
    val nickName: String,

    val avatar: String,

    @ColumnInfo(name = "bank_account")
    val bankAccount: String = "",

    ): Parcelable

