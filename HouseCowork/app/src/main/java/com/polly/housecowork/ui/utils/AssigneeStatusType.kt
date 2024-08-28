package com.polly.housecowork.ui.utils



enum class AssigneeStatusType(val level: Int) {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2),
    CANCELLED(3);

    companion object {
        fun from(level: Int): AssigneeStatusType {
            return entries.first { it.level == level }
        }
    }
}