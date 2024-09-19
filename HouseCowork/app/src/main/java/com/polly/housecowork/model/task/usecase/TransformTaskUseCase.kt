package com.polly.housecowork.model.task.usecase

import android.content.Context
import android.provider.ContactsContract.Profile
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.model.assigneestatus.AssigneeRepository
import com.polly.housecowork.model.profile.DefaultProfileRepository
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

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

    suspend fun toTask(taskDto: TaskDto): Task {
        taskDto.apply {
            return Task(
                id,
                getProfile(profileId = ownerId),
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

    private suspend fun getProfile(fetchRemote: Boolean = false, profileId: Int): ProfileInfo {
        return profileRepository.getProfileById(fetchRemote, profileId )
    }

    private fun getAssignees(assigneeId: Int) = assigneeRepository.getAssigneeStatus(assigneeId)


}