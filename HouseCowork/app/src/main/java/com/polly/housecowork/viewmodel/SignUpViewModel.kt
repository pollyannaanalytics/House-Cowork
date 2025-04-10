package com.polly.housecowork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.network.model.SignUpRequest
import com.polly.housecowork.model.auth.AuthRepository
import com.polly.housecowork.model.auth.AuthType
import com.polly.housecowork.model.auth.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    data class ErrorState(
        val errorEmail: Boolean = false,
        val errorPassword: Boolean = false,
        val errorRepeatedPassword: Boolean = false
    )

    data class SignUpFormState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val repeatPassword: String = "",
        val isEmailValid: Boolean = true,
        val isPasswordValid: Boolean = true,
        val isPasswordMatched: Boolean = true
    ) {
        val isValid: Boolean
            get() = isEmailValid && isPasswordValid && isPasswordMatched &&
                    name.isNotBlank() &&
                    email.isNotBlank() && password.isNotBlank()
    }

    private val _formState = MutableStateFlow(SignUpFormState())
    val formState = _formState.asStateFlow()

    private val _finishState= MutableSharedFlow<FinishSignUpState>()
    val finishState = _finishState.asSharedFlow()

    val errorState = formState.map { state ->
        ErrorState(
            errorPassword = !state.isPasswordValid,
            errorEmail = !state.isEmailValid,
            errorRepeatedPassword = !state.isPasswordMatched
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ErrorState()
    )

    private fun String.isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun setUsername(username: String) {
        _formState.update {
            it.copy(name = username)
        }
    }

    fun setEmail(email: String) {
        _formState.update {
            it.copy(
                email = email,
                isEmailValid = email.isValidEmail()
            )
        }

    }

    fun setPassword(password: String) {
        _formState.update {
            it.copy(
                password = password,
                isPasswordValid = password.checkPasswordValid(),
            )
        }
        Log.e("SignUpViewModel", "password: $password")
    }


    fun confirmSamePassword(repeatPassword: String) {
        _formState.update { currentState ->
            currentState.copy(
                repeatPassword = repeatPassword,
                isPasswordMatched = currentState.password == repeatPassword
            )
        }
    }

    private fun String.checkPasswordValid(): Boolean {
        return length >= 8
                && contains(Regex(".*[A-Z].*"))
                && contains(Regex(".*[0-9].*"))
    }

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val state = _formState.value
                if (!state.isValid) {
                    _finishState.emit(FinishSignUpState.Fail)
                    return@launch
                }

                val request = SignUpRequest(
                    name = state.name,
                    email = state.email,
                    password = state.password,
                    passwordConfirm = state.repeatPassword
                )

                val userState = authRepository.signUp(AuthType.HOUSE_COWORK, request)
                when (userState) {
                    is OnboardingState.Auth.Complete -> {
                        _finishState.emit(FinishSignUpState.Success)
                    }

                    else -> {
                        _finishState.emit(FinishSignUpState.Fail)
                    }
                }
            } catch (e: Exception) {
                _finishState.emit(FinishSignUpState.Fail)
            }
        }
    }

}

sealed class FinishSignUpState {
    data object Success : FinishSignUpState()
    data object Fail : FinishSignUpState()
}