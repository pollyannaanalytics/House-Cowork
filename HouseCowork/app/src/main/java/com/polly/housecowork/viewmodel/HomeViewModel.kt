package com.polly.housecowork.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.task.DefaultTaskRepository
import com.polly.housecowork.ui.utils.DinosaurType
import com.polly.housecowork.model.task.usecase.GenerateDinosaurGrowthUseCase
import com.polly.housecowork.model.task.usecase.TransformTaskUseCase
import com.polly.housecowork.model.task.usecase.TransformTaskUseCase.*
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val generateDinosaurGrowthUseCase: GenerateDinosaurGrowthUseCase,
    private val transformTaskUseCase: TransformTaskUseCase,
    private val prefsLicense: PrefsLicense,
    private val taskRepository: DefaultTaskRepository,
): ViewModel() {

    private val _dinosaurType = MutableStateFlow<DinosaurType>(DinosaurType.Egg)
    val dinosaurType: StateFlow<DinosaurType> = _dinosaurType

    private val _progressTasks = MutableStateFlow(
        emptyList<Task>().toMutableList()
    )
    val progressTasks: StateFlow<List<Task>> = _progressTasks

    private val _doneTasks = MutableStateFlow(
        emptyList<Task>().toMutableList()
    )
    val doneTasks: StateFlow<List<Task>> = _doneTasks

    init {
        getAssignedTasks()
    }

    private fun getAssignedTasks(isRefresh: Boolean = false) {
        viewModelScope.launch {
            taskRepository.getAssignedTasks(
                prefsLicense.userId,
                AssigneeStatusType.ACCEPTED.level,
                isRefresh
            ).collect { taskDtos ->
                val tasks = taskDtos.map { transformTaskUseCase.toTask(it) }
                tasks.forEach { task ->
                    when(task.taskStatus){
                        TaskStatus.IN_PROGRESS -> _progressTasks.value.add(task)
                        TaskStatus.DONE -> _doneTasks.value.add(task)
                        else -> {}
                    }
                }
                generateDinosaurGrowthUseCase.invoke(_doneTasks.value)
            }
        }
    }
}