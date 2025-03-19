package com.polly.housecowork.domain.task

import com.polly.housecowork.model.task.DefaultTaskRepository
import javax.inject.Inject

class GetHomeTasksUseCase @Inject constructor(
    private val taskRepository: DefaultTaskRepository
) {
    suspend fun invoke() = taskRepository.getHomeTasks()
}