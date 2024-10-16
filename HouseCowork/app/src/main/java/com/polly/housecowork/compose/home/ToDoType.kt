package com.polly.housecowork.compose.home

enum class ToDoType(val title: String, val eventOrder: Int) {
    EXPIRED("Expired To-Do", 0),
    TODAY("Today's To-Do", 1),
    TOMORROW("Tomorrow's To-Do", 2),
    FUTURE("Future To-Do", 3),
    THREE_DAYS_FUTURE("Fuuuuuture’s To-Do", 4)
}