package com.polly.housecowork.di

import com.polly.housecowork.domain.onboarding.UpdateOnboardingUseCase
import com.polly.housecowork.domain.profile.GetProfileUseCase
import com.polly.housecowork.domain.profile.ProfileUseCase
import com.polly.housecowork.domain.profile.UpdateProfileUseCase
import com.polly.housecowork.domain.task.GenerateDinosaurGrowthUseCase
import com.polly.housecowork.domain.task.GetHomeTasksUseCase
import com.polly.housecowork.domain.task.MapTaskDetailUseCase
import com.polly.housecowork.domain.task.TaskUseCase
import com.polly.housecowork.prefs.PrefsLicense
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
        mapTaskDetailUseCase: MapTaskDetailUseCase,
        getHomeTasksUseCase: GetHomeTasksUseCase
    ): TaskUseCase {
        return TaskUseCase(generateDinosaurGrowthUseCase, mapTaskDetailUseCase, getHomeTasksUseCase)
    }

    @Provides
    fun provideProfileUseCase(
        updateProfileUseCase: UpdateProfileUseCase,
        getProfileUseCase: GetProfileUseCase
    ): ProfileUseCase {
        return ProfileUseCase(updateProfileUseCase, getProfileUseCase)
    }

    @Provides
    fun provideOnboardingUseCase(
        prefsLicense: PrefsLicense
    ): UpdateOnboardingUseCase {
        return UpdateOnboardingUseCase(prefsLicense)
    }
}