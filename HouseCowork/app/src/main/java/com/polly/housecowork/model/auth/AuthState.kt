package com.polly.housecowork.model.auth

sealed class AuthState {
    object Login : AuthState()
    object LogOut : AuthState()
    object UnAuthenticated : AuthState()
}