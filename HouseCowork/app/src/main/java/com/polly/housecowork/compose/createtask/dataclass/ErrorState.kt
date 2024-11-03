package com.polly.housecowork.compose.createtask.dataclass

data class ErrorState(
    var titleError: Boolean = false,
    var dueTimeError: Boolean = false
)
