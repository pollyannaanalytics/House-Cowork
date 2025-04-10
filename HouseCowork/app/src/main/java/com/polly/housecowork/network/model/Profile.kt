package com.polly.housecowork.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileRequest(
    val name: String,
    val avatar: String,
    val bankAccount: String
): Parcelable

@Parcelize
data class ProfileInfoResponse (
    val user: User
): Parcelable