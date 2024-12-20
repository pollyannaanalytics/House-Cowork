package com.polly.housecowork.dataclass

sealed class FinishSignUpState {
    data object Success : FinishSignUpState()
    data object Fail : FinishSignUpState()
}