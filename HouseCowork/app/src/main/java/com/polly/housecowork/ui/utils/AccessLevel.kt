package com.polly.housecowork.ui.utils


enum class AccessLevel(val level: Int) {
    PUBLIC(0),
    PRIVATE(1);

    companion object {
        fun from(level: Int): AccessLevel {
            return entries.first { it.level == level }
        }
    }
}