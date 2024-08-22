package com.polly.housecowork.dataclass

data class House(
    val id: Int,
    val name: String,
    val description: String,
    val rules: List<String>,
    val members: List<UserInfo>
)