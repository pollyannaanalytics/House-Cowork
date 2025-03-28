package com.polly.housecowork.domain.task

import com.polly.housecowork.dataclass.TaskState
import com.polly.housecowork.ui.utils.DinosaurType
import javax.inject.Inject

class GenerateDinosaurGrowthUseCase @Inject constructor() {
    fun invoke(tasks: List<TaskState>) : DinosaurType {
        val completedCount = tasks.size
        return when(completedCount) {
            in 0..20 -> DinosaurType.Egg
            in 21..40 -> DinosaurType.EggOut
            in 41..60 -> DinosaurType.Dinosaur
            else -> DinosaurType.DinosaurKing
        }
    }

}