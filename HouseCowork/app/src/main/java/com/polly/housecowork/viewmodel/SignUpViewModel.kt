package com.polly.housecowork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.polly.housecowork.dataclass.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {


    data class ErrorState(
        var errorPassword : Boolean = false,
        var errorEmail : Boolean = false,
        var errorRepeatedPassword : Boolean = false
    )
    private val _username = MutableStateFlow("")

    private val _email = MutableStateFlow("")

    private val _password = MutableStateFlow("")

    private val _errorState = MutableStateFlow(ErrorState())
    val errorState = _errorState.asStateFlow()



    fun setUsername(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorState.value = _errorState.value.copy(errorEmail = true)
        } else {
            _errorState.value = _errorState.value.copy(errorEmail = false)
            _email.value = email
        }

    }

    fun setPassword(password: String) {
        Log.e("SignUpViewModel", "password: $password")
        if (!checkPasswordValid(password)) {
            Log.e("SignUptest", "password is invalid")
            _errorState.value = _errorState.value.copy(errorPassword = true)
        } else {
            _errorState.value = _errorState.value.copy(errorPassword = false)
            _password.value = password
        }
    }


    fun confirmSamePassword(repeatPassword: String) {
        _errorState.value = _errorState.value.copy(
            errorRepeatedPassword = _password.value != repeatPassword
        )
    }

    private fun checkPasswordValid(password: String): Boolean {
        return password.length >= 8
                && password.contains(Regex(".*[A-Z].*"))
                && password.contains(Regex(".*[0-9].*"))
    }

    fun checkAllFieldsValid(): Boolean {
        return !_errorState.value.errorEmail
                && !_errorState.value.errorPassword
                && !_errorState.value.errorRepeatedPassword
    }
    fun setUpUserInfo(){
       val newUser = UserInfo(
           username = _username.value,
           email = _email.value,
           password = _password.value
       )
    // save user info to database
    }

}