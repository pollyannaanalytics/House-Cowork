package com.polly.housecowork.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.domain.onboarding.UpdateOnboardingUseCase
import com.polly.housecowork.model.auth.OnboardingState
import com.polly.housecowork.model.house.DefaultHouseRepository
import com.polly.housecowork.network.model.HouseRequest
import com.polly.housecowork.network.model.House
import com.polly.housecowork.ui.utils.compose.AvatarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HouseInfo(
    val name: String,
    val description: String,
    val rules: String,
    val avatarState: AvatarState = AvatarState.Edit,
    val imageUrl: String,
    val isHouseNameErr: Boolean,
)

@HiltViewModel
class CreateHouseViewModel @Inject constructor(
    private val createHouseRepository: DefaultHouseRepository,
    private val onboardingUseCase: UpdateOnboardingUseCase
) : ViewModel() {

    private val _houseInfo: MutableStateFlow<HouseInfo> = MutableStateFlow(
        HouseInfo(
            name = "",
            description = "",
            rules = "",
            imageUrl = "",
            isHouseNameErr = false
        )
    )
    val houseInfo = _houseInfo.asStateFlow()

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

    fun onNameChanged(name: String) {
        _houseInfo .update {
            it.copy(name = name)
        }
    }

    fun onDescriptionChanged(description: String) {
        _houseInfo.update {
            it.copy(description = description)
        }
    }

    fun onRulesChanged(rule: String) {
        _houseInfo.update {
            it.copy(rules = rule)
        }
    }
    fun uploadImage(imageUri: Uri) {
        _houseInfo.update {
            it.copy(imageUrl = imageUri.toString())
        }
    }


    fun createHouse(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _houseInfo.value.let {
                if (!it.name.checkNameIsValid()) return@launch
                val request = HouseRequest(
                    name = it.name,
                    description = it.description,
                    rules = listOf(it.rules),
                    // todo: should add imageUrl
//                imageUrl = it.imageUrl
                )

                val result = createHouseRepository.createHouse(request)
                if (result.isSuccess) {
                    _houseDetail.update { result.getOrNull() ?: it }
                    onboardingUseCase.invoke(OnboardingState.Onboarding.CreateHouse)
                    onSuccess()
                }
            }
        }
    }

    private fun String.checkNameIsValid() = this.isNotEmpty() && this.length <= 20

}