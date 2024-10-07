package com.polly.housecowork.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polly.housecowork.compose.home.ToDoType
import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.task.DefaultTaskRepository
import com.polly.housecowork.ui.utils.DinosaurType
import com.polly.housecowork.model.task.usecase.GenerateDinosaurGrowthUseCase
import com.polly.housecowork.model.task.usecase.TransformTaskUseCase
import com.polly.housecowork.model.task.usecase.TransformTaskUseCase.*
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    private val _progressTasks = MutableStateFlow<Map<ToDoType, List<Task>>>(
        mapOf(
            ToDoType.EXPIRED to emptyList(),
            ToDoType.TODAY to emptyList(),
            ToDoType.TOMORROW to emptyList(),
            ToDoType.FUTURE to emptyList(),
            ToDoType.THREE_DAYS_FUTURE to emptyList()
        )

    )
    val progressTasks: StateFlow<Map<ToDoType, List<Task>>> = _progressTasks

    private val _doneTasks = MutableStateFlow(
        emptyList<Task>().toMutableList()
    )
    val doneTasks: StateFlow<List<Task>> = _doneTasks

    init { getAssignedTasks() }

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
                        TaskStatus.IN_PROGRESS -> _progressTasks.value = groupTasksByDueDate(tasks)
                        TaskStatus.DONE -> _doneTasks.value.add(task)
                        else -> {}
                    }
                }
                generateDinosaurGrowthUseCase.invoke(_doneTasks.value)
            }
        }
    }

    private fun groupTasksByDueDate(tasks: List<Task>): Map<ToDoType, List<Task>> {
        val expiredTasks = mutableListOf<Task>()
        val todayTasks = mutableListOf<Task>()
        val tomorrowTasks = mutableListOf<Task>()
        val futureTasks = mutableListOf<Task>()
        val threeDaysFutureTasks = mutableListOf<Task>()

        val progressTasks: MutableMap<ToDoType, List<Task>> = mutableMapOf(
            ToDoType.EXPIRED to expiredTasks,
            ToDoType.TODAY to todayTasks,
            ToDoType.TOMORROW to tomorrowTasks,
            ToDoType.FUTURE to futureTasks,
            ToDoType.THREE_DAYS_FUTURE to threeDaysFutureTasks
        )
        tasks.forEach { task ->
            when(getToDoType(task.dueDate)){
                ToDoType.EXPIRED -> expiredTasks.add(task)
                ToDoType.TODAY -> todayTasks.add(task)
                ToDoType.TOMORROW -> tomorrowTasks.add(task)
                ToDoType.FUTURE -> futureTasks.add(task)
                ToDoType.THREE_DAYS_FUTURE -> threeDaysFutureTasks.add(task)
            }
        }

        return  mapOf(
            ToDoType.TODAY to listOf(
                Task(
                    id = 1,
                    owner = ProfileInfo(
                        name = "Mock Profile",
                        nickName = "Mock Profile Description",
                        avatar = "https://mock.com",
                        bankAccount = "23232323",
                        email = "pinyunwuu@gmail.com",
                        updateTime = 123232323
                    ),
                    title = "Grocery Shopping",
                    description = "Buy milk, eggs, bread",
                    accessLevel = AccessLevel.PRIVATE,
                    taskStatus = TaskStatus.IN_PROGRESS,
                    dueDate = "2023-07-28",
                    dueTime = "14:30",
                    assigneeStatus = emptyList(),
                    createdTime = 1690886400000,
                    updatedTime = 1690886400000
                ),
                Task(
                    id = 2,
                    owner = ProfileInfo(
                        name = "Mock Profile",
                        nickName = "Mock Profile Description",
                        avatar = "https://mock.com",
                        bankAccount = "23232323",
                        email = "pinyunwuu@gmail.com",
                        updateTime = 123232323
                    ),
                    title = "Book Doctor Appointment",
                    description = "Schedule a check-up",
                    accessLevel = AccessLevel.PRIVATE,
                    taskStatus = TaskStatus.IN_PROGRESS,
                    dueDate = "2023-07-28",
                    dueTime = "14:30",
                    assigneeStatus = emptyList(),
                    createdTime = 1690897200000,
                    updatedTime = 1690897200000
                )
            ),
            ToDoType.TOMORROW to listOf(
                Task(
                    id = 3,
                    owner = ProfileInfo(
                        name = "Mock Profile",
                        nickName = "Mock Profile Description",
                        avatar = "https://mock.com",
                        bankAccount = "23232323",
                        email = "pinyunwuu@gmail.com",
                        updateTime = 123232323
                    ),
                    title = "Pay Bills",
                    description = "Pay electricity and internet bills",
                    accessLevel = AccessLevel.PRIVATE,
                    taskStatus = TaskStatus.IN_PROGRESS,
                    dueDate = "2023-07-28",
                    dueTime = "14:30",
                    assigneeStatus = emptyList(),
                    createdTime = 1690972800000,
                    updatedTime = 1690972800000
                )
            )
        )
    }

    private fun getToDoType(dateString: String): ToDoType {
        val comparisonResult = compareDateWithToday(dateString)
        val today = LocalDate.now()

        return when {
            comparisonResult < 0 -> ToDoType.EXPIRED
            comparisonResult == 0 -> ToDoType.TODAY
            today.plusDays(1).isEqual(LocalDate.parse(dateString)) -> ToDoType.TOMORROW
            today.plusDays(2).isEqual(LocalDate.parse(dateString)) ||
                    today.plusDays(3).isEqual(LocalDate.parse(dateString)) -> ToDoType.FUTURE
            else -> ToDoType.THREE_DAYS_FUTURE
        }
    }

    fun compareDateWithToday(dateString: String): Int {
        val date = LocalDate.parse(dateString)
        val today = LocalDate.now()
        return date.compareTo(today)
    }
}