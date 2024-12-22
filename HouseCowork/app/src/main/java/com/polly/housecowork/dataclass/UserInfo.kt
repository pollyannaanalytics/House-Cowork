package com.polly.housecowork.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SignUpRequest(
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val name: String,
    val nickName: String
): Parcelable


@Parcelize
data class SignInRequest(
    val email: String,
    val password: String
): Parcelable

@Parcelize
data class AuthData(
    val user: User,
    val accessToken: String
) : Parcelable

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val nickName: String,
    val avatar: String = ""
) : Parcelable

fun User.toProfileInfo(): ProfileInfo {
    return ProfileInfo(
        id = id,
        name = name,
        email = email,
        nickName = nickName,
        avatar = avatar?:"",
        bankAccount = "",
        bio = ""
    )
}


