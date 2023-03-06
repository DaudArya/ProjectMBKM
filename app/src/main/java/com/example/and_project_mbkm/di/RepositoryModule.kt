package com.example.and_project_mbkm.di

import com.example.and_project_mbkm.data.repository.AuthApiRepository
import com.example.and_project_mbkm.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: UserRepositoryImpl): AuthApiRepository

}