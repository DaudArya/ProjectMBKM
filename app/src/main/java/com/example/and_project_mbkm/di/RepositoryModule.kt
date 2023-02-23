package com.example.and_project_mbkm.di

import com.example.and_project_mbkm.data.local.dao.UserDao
import com.example.and_project_mbkm.data.repository.AuthRepository
import com.example.and_project_mbkm.data.repository.impl.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideAuthRepository(userDao: UserDao): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }

}