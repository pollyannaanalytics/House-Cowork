package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Calendar(
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,
    val title: String,
    val description: String,

    @ColumnInfo(name = "dt_start")
    val dtStart: Long,

    @ColumnInfo(name = "dt_end")
    val dtEnd: Long,

    @ColumnInfo(name = "guests_can_see_guests")
    val guestsCanSeeGuests: Boolean
)
