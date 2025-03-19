package com.polly.housecowork.domain.task


data class TaskUseCase (
    val generateDinosaurGrowthUseCase: GenerateDinosaurGrowthUseCase,
    val mapTaskDetailUseCase: MapTaskDetailUseCase,
    val getHomeTasksUseCase: GetHomeTasksUseCase,
)
