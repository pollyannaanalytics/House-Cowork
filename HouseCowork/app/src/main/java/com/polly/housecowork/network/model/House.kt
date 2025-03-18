package com.polly.housecowork.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HouseRequest(
    val name: String,
    val description: String,
    val rules: List<String>
): Parcelable


@Parcelize
data class HouseCreateResponse(
    val houseResponse: HouseResponse
): Parcelable


@Parcelize
data class HouseResponse(
    val id: Int,
    val name: String,
    val description: String,
    val rules: String,
    val memberIds: List<Int>
): Parcelable
