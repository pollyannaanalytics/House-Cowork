package com.polly.housecowork.usecase.task

import com.polly.housecowork.dataclass.AssignedTask
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.assigneestatus.AssigneeRepository
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.model.task.DefaultTaskRepository
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransformTaskUseCase @Inject constructor(
    private val taskRepository: DefaultTaskRepository,
    private val profileRepository: DefaultProfileRepository,
    private val assigneeRepository: AssigneeRepository,
) {

    suspend fun invoke(
        userId: Int,
        assigneeStatusType: AssigneeStatusType,
        fetchRemote: Boolean
    ): Flow<Result<List<AssignedTask>>> {
        val resultFlow =
            taskRepository.getAssignedTasks(userId, assigneeStatusType.level, fetchRemote)
        return resultFlow.map { result ->
            result.map { taskDtos ->
                taskDtos.map { transformTask(it) }
            }
        }
    }

    private suspend fun transformTask(taskDto: TaskDto): AssignedTask {
        val owner = getProfile(taskDto.ownerId, true)
        val assigneeStatus = getAssignees(taskDto.assigneeStatusId, taskDto.id)
        return AssignedTask(
            taskDto.id,
            owner,
            taskDto.title,
            taskDto.description,
            AccessLevel.from(taskDto.accessLevel),
            TaskStatus.from(taskDto.taskStatus),
            formatDate(taskDto.dueTime),
            formatTime(taskDto.dueTime),
            assigneeStatus.status,
            taskDto.createdTime,
            taskDto.updatedTime
        )
    }

    private fun formatTime(isoTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val localDateTime = LocalDateTime.parse(isoTime, formatter)
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun formatDate(isoTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val localDateTime = LocalDateTime.parse(isoTime, formatter)
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }


    private suspend fun getProfile(profileId: Int, fetchRemote: Boolean = false, ): ProfileInfo {
        return profileRepository.getProfileById(profileId, fetchRemote)
    }

    private fun getAssignees(assigneeId: Int, taskId: Int) = assigneeRepository.getAssigneeStatus(assigneeId, taskId)


}