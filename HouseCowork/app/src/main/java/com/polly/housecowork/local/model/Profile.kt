package com.polly.housecowork.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val email: String,
    val bio: String = "",
    @ColumnInfo(name = "nick_name")
    val nickName: String,
    val avatar: String,
    @ColumnInfo(name = "bank_account")
    val bankAccount: String = ""
): Parcelable

