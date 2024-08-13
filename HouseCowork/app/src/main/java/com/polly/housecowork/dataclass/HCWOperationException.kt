package com.polly.housecowork.dataclass

sealed class HCWOperationException(msg: String): Exception(msg) {
    data class MissingPermissions(val missingPermissions: List<String>): HCWOperationException("Missing permissions: $missingPermissions")
}