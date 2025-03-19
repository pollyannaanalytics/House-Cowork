package com.polly.housecowork.model.task

import com.polly.housecowork.dataclass.Assignee
import com.polly.housecowork.dataclass.TaskDomain
import com.polly.housecowork.local.TaskDao
import com.polly.housecowork.local.model.Task
import com.polly.housecowork.local.model.TaskWithAssignees
import javax.inject.Inject

interface TaskLocalDataSource {

    suspend fun getHomeTasks(): List<TaskDomain>

    suspend fun insertCreatedTask(taskDomain: TaskDomain)

    suspend fun updateTasks(taskDomains: List<TaskDomain>)

    suspend fun deleteTask(taskId: Int)
}

class DefaultTaskLocalDataSource @Inject constructor(private val taskDao: TaskDao) : TaskLocalDataSource {

    override suspend fun getHomeTasks(): List<TaskDomain> {
        val tasks = taskDao.getTasks()
        return tasks.map { task ->
            handleDto(task)
        }
    }

    override suspend fun insertCreatedTask(
        taskDomain: TaskDomain
    ){


    }

    override suspend fun updateTasks(taskDomains: List<TaskDomain>) {
        taskDao.updateTasks(
            taskDomains.map { task ->
                handleDomain(task)
            }
        )
    }

    override suspend fun deleteTask(taskId: Int){
        taskDao.deleteTask(taskId)
    }

    private fun handleDto(task: TaskWithAssignees): TaskDomain {
        return TaskDomain(
            id = task.task.id,
            title = task.task.title,
            description = task.task.description,
            accessLevel = task.task.accessLevel,
            dueTime = task.task.dueTime,
            status = task.task.status,
            ownerId = task.task.ownerId,
            assignees = task.assignees.map { assignee ->
                Assignee(
                    id = assignee.assigneeId,
                    status = assignee.status
                )
            }
        )
    }

    private fun handleDomain(task: TaskDomain): Task {
        return Task(
            id = task.id,
            ownerId = task.ownerId,
            title = task.title,
            description = task.description,
            accessLevel = task.accessLevel,
            status = task.status,
            dueTime = task.dueTime
        )
    }

}