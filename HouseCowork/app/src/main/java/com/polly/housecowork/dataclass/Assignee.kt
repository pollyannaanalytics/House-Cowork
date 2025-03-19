package com.polly.housecowork.dataclass

import android.os.Parcelable
import com.polly.housecowork.local.model.Profile
import kotlinx.parcelize.Parcelize

@Parcelize
data class Assignee(
    val id: Int,
    val status: Int
) : Parcelable

data class AssigneeState(
    val assignee: Profile,
    val status: Int
)