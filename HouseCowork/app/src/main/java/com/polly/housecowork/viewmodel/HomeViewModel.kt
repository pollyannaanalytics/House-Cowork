package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _clickEvent = MutableStateFlow<OnClickEvent>(OnClickEvent.OnTaskListClick)
    val clickEvent: StateFlow<OnClickEvent> = _clickEvent

    fun onTaskDetailClick(onClickEvent: OnClickEvent) {
        when(onClickEvent) {
            is OnClickEvent.OnTaskDetailClick -> _clickEvent.value = OnClickEvent.OnTaskDetailClick
            is OnClickEvent.OnCalendarClick -> _clickEvent.value = OnClickEvent.OnCalendarClick
            is OnClickEvent.OnTaskListClick -> _clickEvent.value = OnClickEvent.OnTaskListClick
            is OnClickEvent.OnProfileClick -> _clickEvent.value = OnClickEvent.OnProfileClick
        }
    }
    sealed class OnClickEvent {
        data object OnTaskDetailClick: OnClickEvent()
        data object OnCalendarClick: OnClickEvent()
        data object OnTaskListClick: OnClickEvent()
        data object OnProfileClick: OnClickEvent()
    }
}