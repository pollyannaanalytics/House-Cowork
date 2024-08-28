package com.polly.housecowork.ui.utils


enum class TaskStatus(val level: Int) {
    OPEN(0),
    IN_PROGRESS(1),
    DONE(2),
    CANCELLED(3);

    companion object {
        fun from(level: Int): TaskStatus {
            return values().first { it.level == level }
        }
    }
}