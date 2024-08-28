package com.polly.housecowork.model.assigneestatus

import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.ui.utils.AssigneeStatusType
import javax.inject.Inject


class AssigneeRepository @Inject constructor(){

    fun getAssigneeStatus(assigneeId: Int): List<AssigneeStatus>{
        return listOf(
            AssigneeStatus(1, 1, 1, AssigneeStatusType.PENDING.level),
        )
    }

    fun updateAssigneeStatus(assigneeStatus: AssigneeStatus) {


    }
}