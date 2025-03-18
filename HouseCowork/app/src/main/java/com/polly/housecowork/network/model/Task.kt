package com.polly.housecowork.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskCreateRequest(
    val title: String,
    val description: String,
    val accessLevel: Int,
    val dueTime: String,
    val assigneeIds: List<Int>
): Parcelable


@Parcelize
data class TaskResponse(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val accessLevel: Int,
    val status: Int,
    val dueTime: String,
    val assignees: List<Assignee>
) : Parcelable

@Parcelize
data class Assignee(
    val id: Int,
    val status: Int
) : Parcelable