package com.polly.housecowork.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class House(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val rules: String,
    val members: Int,
    val updateTime: Long
)


@Entity
data class HouseMembers(
    @PrimaryKey
    val id: Int,
    val houseId: Int,
    val userId: Int,
    val updateTime: Long
)