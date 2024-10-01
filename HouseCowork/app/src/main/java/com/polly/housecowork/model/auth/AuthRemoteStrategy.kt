package com.polly.housecowork.model.auth

import com.polly.housecowork.dataclass.ApiResult
import java.util.UUID
import javax.inject.Inject

class HouseCoworkAuthStrategy @Inject constructor() : AuthStrategy{
    override fun checkAuthState(userId:Int) : ApiResult<Unit> {
        return ApiResult.Success(Unit)

    }

    override fun register(): ApiResult<Int> {
        return ApiResult.Success(12333)
    }

    override fun login(): ApiResult<Unit> {
        return ApiResult.Success(Unit)
    }

    override fun logout(): ApiResult<Unit> {
         return ApiResult.Success(Unit)
    }
}