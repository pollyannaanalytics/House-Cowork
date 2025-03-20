package com.polly.housecowork.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Assignee(
    val id: Int,
    val status: Int
) : Parcelable
