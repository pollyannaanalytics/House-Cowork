package com.polly.housecowork.dataclass

data class Calendar(
    val id: Int,
    val title: String,
    val description: String,
    val dtStart: Long,
    val dtEnd: Long,
    val guestsCanSeeGuests: Boolean
)
