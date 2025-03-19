package com.polly.housecowork.domain.task

import com.polly.housecowork.dataclass.AssigneeState
import com.polly.housecowork.dataclass.TaskDomain
import com.polly.housecowork.dataclass.TaskState
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.TaskStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class MapTaskDetailUseCase @Inject constructor(
    val profileRepository: DefaultProfileRepository,
    val prefsLicense: PrefsLicense
) {

    suspend fun invoke(tasksDomains: List<TaskDomain>) = tasksDomains.map { taskDomain ->
        taskDomain.toTaskState()
    }

    private suspend fun TaskDomain.toTaskState() = TaskState(
        id = id,
        title = title,
        description = description,
        accessLevel = AccessLevel.from(accessLevel),
        owner = profileRepository.getProfileById(ownerId),
        dueTime = formatTime(dueTime),
        dueDate = formatDate(dueTime),
        assignees = assignees.map { assignee ->
            val profile = profileRepository.getProfileById(assignee.id)
            AssigneeState(
                assignee = profile,
                status = assignee.status,
            )
        },
        status = TaskStatus.from(status)
    )

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


}
