package com.polly.housecowork.di

import com.polly.housecowork.data.network.AuthApiService
import com.polly.housecowork.model.auth.AuthStrategy
import com.polly.housecowork.model.auth.AuthType
import com.polly.housecowork.model.auth.HouseCoworkAuthStrategy
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class AuthTypeKey(val value: AuthType)

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @IntoMap
    @AuthTypeKey(AuthType.HOUSE_COWORK)
    fun bindHouseCoworkStrategy(impl: HouseCoworkAuthStrategy): AuthStrategy

}