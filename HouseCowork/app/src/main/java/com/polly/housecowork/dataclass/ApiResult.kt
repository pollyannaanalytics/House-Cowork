package com.polly.housecowork.dataclass

sealed class ApiResult(
    val code: Int,
    val message: String,
) {
    data class Success(
        val data: Any
    ) : ApiResult(200, "Success")

    data class Failure(
        val reason: Reason
    ) : ApiResult(400, "Failure") {
        sealed class Reason {
            data class NoData(val e164: String) : Reason()
            data class QueryFailed(val e: Exception, val e164: String) : Reason()
            data class PermissionDenied(val permission: String) : Reason()
        }
    }
}

