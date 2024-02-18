package com.oybekdev.lokmart.di

import com.oybekdev.lokmart.data.repo.AuthRepositoryImpl
import com.oybekdev.lokmart.domain.repo.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ):AuthRepository
}