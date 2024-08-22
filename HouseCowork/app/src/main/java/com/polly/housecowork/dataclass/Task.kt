package com.polly.housecowork.dataclass



data class Task(
    val id: Int,
    val owner: UserInfo,
    val title: String,
    val description: String,
    val publicStatus: Int,
    val status: Int,
    val dueTime: String,
    val assignees: List<UserInfo>,
    val house: House
)