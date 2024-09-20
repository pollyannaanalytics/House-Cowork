package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import com.polly.housecowork.dataclass.House
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HouseListViewModel @Inject constructor(): ViewModel() {

    private val _houseList = MutableStateFlow<List<House>>(emptyList())
    val houseList = _houseList.asStateFlow()

    fun getHouseList() {

    }
}