package com.oybekdev.lokmart.di

import com.oybekdev.lokmart.data.repo.AuthRepositoryImpl
import com.oybekdev.lokmart.data.repo.ProductRepositoryImpl
import com.oybekdev.lokmart.domain.repo.AuthRepository
import com.oybekdev.lokmart.domain.repo.ProductRepository
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
    @Binds
    abstract fun bindProductRepository(
        authRepositoryImpl: ProductRepositoryImpl
    ):ProductRepository
}