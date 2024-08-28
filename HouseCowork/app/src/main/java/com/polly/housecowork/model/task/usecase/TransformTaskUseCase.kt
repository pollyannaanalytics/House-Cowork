package com.polly.housecowork.model.task.usecase

import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.assigneestatus.AssigneeRepository
import com.polly.housecowork.model.profile.ProfileRepository
import com.polly.housecowork.model.task.TaskRepository
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransformTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val profileRepository: ProfileRepository,
    private val assigneeRepository: AssigneeRepository,
    private val prefsLicense: PrefsLicense
){
    fun Task.toTaskDto(): TaskDto {
        apply {
            return TaskDto(
                id,
                owner.id,
                title,
                description,
                accessLevel.level,
                taskStatus.level,
                dueTime,
                assigneeStatus.first().id,
                prefsLicense.houseId,
                createdTime,
                updatedTime
            )
        }
    }

    private fun TaskDto.toTask(): Task {
        apply {
            return Task(
                id,
                getProfile(ownerId),
                title,
                description,
                AccessLevel.from(accessLevel),
                TaskStatus.from(taskStatus),
                dueTime,
                getAssignees(assigneeStatusId),
                createdTime,
                updatedTime
            )
        }
    }

    suspend fun getAssignedTasks(
        assigneeStatusId: Int = prefsLicense.userId,
        assigneeStatusType: AssigneeStatusType,
        isRefresh: Boolean = false
    ): Flow<List<Task>> {
        return taskRepository.getAssignedTasks(
            assigneeStatusId,
            assigneeStatusType.level,
            isRefresh
        ).map {
            it.map { taskDto -> taskDto.toTask() }
        }
    }

    suspend fun createTask(task: Task) {
        taskRepository.createTask(task.toTaskDto())
    }

    suspend fun deleteTaskById(taskId: Int) {
        taskRepository.deleteTaskById(taskId)
    }




    private fun getProfile(profileId: Int) = profileRepository.getProfileById(profileId)

    private fun getAssignees(assigneeId: Int) = assigneeRepository.getAssigneeStatus(assigneeId)


}