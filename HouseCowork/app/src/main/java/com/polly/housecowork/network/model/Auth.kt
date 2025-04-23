package com.polly.housecowork.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SignUpRequest(
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val name: String
): Parcelable

@Parcelize
data class SignInRequest(
    val email: String,
    val password: String
): Parcelable

@Parcelize
data class AuthResponse(
    val user: User,
    val accessToken: String
) : Parcelable

@Parcelize
data class UserResponse(
    val user: User
) : Parcelable

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String = ""
) : Parcelable





