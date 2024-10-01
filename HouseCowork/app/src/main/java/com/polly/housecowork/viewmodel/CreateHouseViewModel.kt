package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateHouseViewModel @Inject constructor(): ViewModel (){
    private var _nameErrState: MutableStateFlow<HouseNameError> = MutableStateFlow(HouseNameError())
    val nameErrState = _nameErrState.asStateFlow()

    fun createHouse(houseName: String, description: String, rules: String){
        if (!checkNameIsValid(houseName)) return


    }

    private fun checkNameIsValid(houseName: String): Boolean{
        if (houseName.isEmpty()){
           _nameErrState.value = _nameErrState.value.copy(houseNameEmpty = true)
            return false
        }

        if(houseName.length < 3){
            _nameErrState.value = _nameErrState.value.copy(houseNameError = true)
            return false
        }
        return true
    }


    data class HouseNameError(
        var houseNameError: Boolean = false,
        var houseNameEmpty: Boolean = false
    )
}