package com.polly.housecowork.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.model.task.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
//    private val getUserCalendarsUseCase: GetUserCalendarsUseCase,
): ViewModel() {

    private val _profileInfo = MutableStateFlow<ProfileInfo?>(null)
    val profileInfo = _profileInfo.asStateFlow()

    fun getProfileById(profileId: Int, fetchRemote: Boolean = false){
       viewModelScope.launch {
             _profileInfo.value =  getProfileUseCase.invoke(fetchRemote, profileId)
       }
    }

    fun getUserCalendars(profileId: Int, fetchRemote: Boolean = false){
       viewModelScope.launch {
//              getUserCalendarsUseCase.invoke(profileId, fetchRemote).collect { calendars ->
//                  // do something with calendars
//              }
       }
    }



}