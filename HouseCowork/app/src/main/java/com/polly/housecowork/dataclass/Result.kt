package com.polly.housecowork.dataclass

sealed class Result<out T>{
    data class Success<out T>(
        val data: Any
    ) : Result<T>()

    data class Error(
        val e: Exception
    ) : Result<Nothing>() {
        sealed class Reason(message: String): Exception(message = message) {
            data class NoData(val queryStr: String) : Reason(message = "No data found: $queryStr")
            data class SearchFailed(val e: Exception) : Reason(message = "Search failed: ${e.message}")
            data class PermissionDenied(val e: Exception, val requiredPermissions: List<String>) : Reason(message = "Permission denied: ${requiredPermissions.joinToString()}")
        }
    }
}




