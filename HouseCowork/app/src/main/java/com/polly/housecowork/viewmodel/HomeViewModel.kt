package com.polly.housecowork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.compose.home.ToDoType
import com.polly.housecowork.dataclass.TaskState
import com.polly.housecowork.ui.utils.DinosaurType
import com.polly.housecowork.ui.utils.TaskStatus
import com.polly.housecowork.domain.task.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private val initProgressTasksMap: Map<ToDoType, List<TaskState>> = mapOf(
    ToDoType.EXPIRED to emptyList(),
    ToDoType.TODAY to emptyList(),
    ToDoType.TOMORROW to emptyList(),
    ToDoType.FUTURE to emptyList(),
    ToDoType.THREE_DAYS_FUTURE to emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {

    private val _dinosaurType = MutableStateFlow<DinosaurType>(DinosaurType.Egg)
    val dinosaurType: StateFlow<DinosaurType> = _dinosaurType

    private val _progressTasks = MutableStateFlow(initProgressTasksMap)
    val progressTasks: StateFlow<Map<ToDoType, List<TaskState>>> = _progressTasks

    private val _doneTasks = MutableStateFlow(emptyList<TaskState>())
    val doneTasks: StateFlow<List<TaskState>> = _doneTasks

    private val _shouldSeeMore = MutableStateFlow(false)
    val shouldSeeMore: StateFlow<Boolean> = _shouldSeeMore.asStateFlow()

    init {
        getAssignedTasks()
    }

    private fun getAssignedTasks() {
        viewModelScope.launch {
            val tasks = taskUseCase.getHomeTasksUseCase.invoke()
            val tasksWithDetails = taskUseCase.mapTaskDetailUseCase.invoke(tasks)
            processTasks(tasksWithDetails)
        }
    }

    private fun groupTasksByDueDate(tasks: List<TaskState>): Map<ToDoType, List<TaskState>> {
        val groupedTasks = tasks.groupBy { getToDoType(it.dueDate) }

        return initProgressTasksMap.mapValues { (todoType, _) ->
            val tasksForType = groupedTasks[todoType] ?: emptyList()

            if (tasksForType.size < 5) return@mapValues tasksForType
            tasksForType.take(5)
        }
    }


    private fun processTasks(tasks: List<TaskState>) {
        val doneTasks = mutableListOf<TaskState>()
        val progressTasks = mutableListOf<TaskState>()

        tasks.forEach { task ->
            when (task.status) {
                TaskStatus.DONE -> doneTasks.add(task)
                TaskStatus.IN_PROGRESS -> progressTasks.add(task)
                TaskStatus.OPEN, TaskStatus.CANCELLED -> {}
            }
        }

        _progressTasks.value = groupTasksByDueDate(progressTasks)
        _dinosaurType.value = taskUseCase.generateDinosaurGrowthUseCase.invoke(doneTasks)
        _doneTasks.value = doneTasks
        Log.d("HomeViewModel", "processTasks: ${_progressTasks.value}")
    }

    private fun getToDoType(dateString: String): ToDoType {
        val dueDate = LocalDate.parse(dateString)
        val today = LocalDate.now()

        return when {
            dueDate.isBefore(today) -> ToDoType.EXPIRED
            dueDate.isEqual(today) -> ToDoType.TODAY
            dueDate.isEqual(today.plusDays(1)) -> ToDoType.TOMORROW
            dueDate.isBefore(today.plusDays(4)) && dueDate.isAfter(today.plusDays(1)) -> ToDoType.THREE_DAYS_FUTURE
            else -> ToDoType.FUTURE
        }
    }


}