package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.model.house.DefaultHouseRepository
import com.polly.housecowork.network.model.House
import com.polly.housecowork.network.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDetailViewModel @Inject constructor(
    private val houseRepository: DefaultHouseRepository
) : ViewModel() {

    private val _houseDetail: MutableStateFlow<House> = MutableStateFlow(
        House(
            id = -1,
            name = "",
            description = "",
            rules = emptyList(),
            memberIds = emptyList()
        )
    )

    val houseDetail = _houseDetail.asStateFlow()

    private val _members: MutableStateFlow<List<User>> = MutableStateFlow(
        emptyList()
    )

    val members = _members.asStateFlow()

    fun fetchHouseDetail(id: Int) {
        viewModelScope.launch {
            houseRepository.getHouseInfo(id).onSuccess { result ->
                result?.let { houseInfo ->
                    _houseDetail.update {
                        House(
                            id = houseInfo.id,
                            name = houseInfo.name,
                            description = houseInfo.description,
                            rules = houseInfo.rules,
                            memberIds = houseInfo.memberIds
                        )
                    }
                    val memberList = mutableListOf<User>()
                    houseInfo.memberIds.forEach { memberId ->
                        val user = houseRepository.getUserInfo(memberId)
                        user?.let { memberList.add(it) }
                    }
                    _members.update { memberList }
                }
            }
        }
    }
}