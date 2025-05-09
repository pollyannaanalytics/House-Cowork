package com.polly.housecowork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.model.house.DefaultHouseRepository
import com.polly.housecowork.network.model.House
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseListViewModel @Inject constructor(
    private val houseRepository: DefaultHouseRepository
): ViewModel() {

    private val _houseList = MutableStateFlow<List<House>>(emptyList())
    val houseList = _houseList.asStateFlow()

    fun getHouseList() {
        viewModelScope.launch {
            try {
                houseRepository.getHouses()?.onSuccess { houses ->
                    _houseList.value = houses
                }
            } catch (e: Error) {
                Log.d("HouseList", "fetch house list failed: ${e.message}")
            }
        }
    }
}