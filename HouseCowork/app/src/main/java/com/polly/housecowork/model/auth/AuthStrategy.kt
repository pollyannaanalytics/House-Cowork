package com.polly.housecowork.model.auth

import com.polly.housecowork.dataclass.ApiResult

interface AuthStrategy {
    fun checkAuthState(userId: Int): ApiResult<Unit>
    fun register(): ApiResult<Int>
    fun login(): ApiResult<Unit>
    fun logout(): ApiResult<Unit>
}