package com.polly.housecowork.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class House(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val rules: List<String>,
    val members: List<UserInfo>
)