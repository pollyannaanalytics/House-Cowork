package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import com.polly.housecowork.ui.utils.DinosaurType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _clickEvent = MutableStateFlow<OnClickEvent>(OnClickEvent.OnTaskListClick)
    val clickEvent: StateFlow<OnClickEvent> = _clickEvent

    private val _dinosaurType = MutableStateFlow<DinosaurType>(DinosaurType.DinosaurEgg)
    val dinosaurType: StateFlow<DinosaurType> = _dinosaurType
    fun transferToDinosaur(taskCount: Int){
        when(taskCount){
            in 0..5 -> _dinosaurType.value = DinosaurType.DinosaurEgg
            in 6..10 -> _dinosaurType.value = DinosaurType.DinosaurBaby
            in 11..15 -> _dinosaurType.value = DinosaurType.DinosaurAdult
            else -> _dinosaurType.value = DinosaurType.DinosaurWithWings
        }
    }



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