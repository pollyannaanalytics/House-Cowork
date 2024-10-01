package com.polly.housecowork.di

import com.polly.housecowork.model.auth.AuthStrategy
import com.polly.housecowork.model.auth.HouseCoworkAuthStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @IntoMap
    @StringKey("house_cowork")
    fun provideNativeAuthStrategy(): AuthStrategy {
        return HouseCoworkAuthStrategy()
    }

}