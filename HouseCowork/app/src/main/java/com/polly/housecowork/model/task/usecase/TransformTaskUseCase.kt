package com.polly.housecowork.model.task.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.assigneestatus.AssigneeRepository
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.TaskStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransformTaskUseCase @Inject constructor(
    private val profileRepository: DefaultProfileRepository,
    private val assigneeRepository: AssigneeRepository,
    private val prefsLicense: PrefsLicense
){
   fun toTaskDto(task: Task): TaskDto {
       task.apply {
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun toTask(taskDto: TaskDto): Task {
        taskDto.apply {
            return Task(
                id,
                getProfile(profileId = ownerId),
                title,
                description,
                AccessLevel.from(accessLevel),
                TaskStatus.from(taskStatus),
                formateDate(dueTime),
                formatTime(dueTime),
                getAssignees(assigneeStatusId),
                createdTime,
                updatedTime
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatTime(isoTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val localDateTime = LocalDateTime.parse(isoTime, formatter)
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formateDate(isoTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val localDateTime = LocalDateTime.parse(isoTime, formatter)
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }


    private suspend fun getProfile(fetchRemote: Boolean = false, profileId: Int): ProfileInfo {
        return profileRepository.getProfileById(fetchRemote, profileId )
    }

    private fun getAssignees(assigneeId: Int) = assigneeRepository.getAssigneeStatus(assigneeId)


}