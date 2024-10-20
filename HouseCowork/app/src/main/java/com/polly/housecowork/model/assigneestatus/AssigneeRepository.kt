package com.polly.housecowork.model.assigneestatus

import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.ui.utils.AssigneeStatusType
import com.polly.housecowork.ui.utils.TaskStatus
import javax.inject.Inject


class AssigneeRepository @Inject constructor(){

    fun getAssigneeStatus(assigneeId: Int, taskId: Int): AssigneeStatus{
        return AssigneeStatus(1, 1, 1, TaskStatus.IN_PROGRESS.level)

    }

    fun updateAssigneeStatus(assigneeStatus: AssigneeStatus) {


    }
}