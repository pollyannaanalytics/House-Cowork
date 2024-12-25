package com.polly.housecowork.model.auth

sealed class AuthState {
    data class Login(val userId: Int) : AuthState()
    data object LogOut : AuthState()
    data object UnAuthenticated : AuthState()
}

enum class AuthType {
    HOUSE_COWORK,
    GOOGLE,
}