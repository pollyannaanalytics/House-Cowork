package com.polly.housecowork.di

import com.polly.housecowork.domain.task.GenerateDinosaurGrowthUseCase
import com.polly.housecowork.domain.task.TaskUseCase
import com.polly.housecowork.domain.task.TransformTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideTaskUseCase(
        generateDinosaurGrowthUseCase: GenerateDinosaurGrowthUseCase,
        transformTaskUseCase: TransformTaskUseCase
    ): TaskUseCase {
        return TaskUseCase(generateDinosaurGrowthUseCase, transformTaskUseCase)
    }
}