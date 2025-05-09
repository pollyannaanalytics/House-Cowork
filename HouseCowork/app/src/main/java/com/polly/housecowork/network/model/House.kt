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
    val house: House
): Parcelable

@Parcelize
data class GetOwnHousesResponse(
    val houses: List<House>
): Parcelable

@Parcelize
data class House(
    val id: Int,
    val name: String,
    val description: String,
    val rules: List<String>,
    val memberIds: List<Int>
): Parcelable
