package com.example.and_project_mbkm.di

import com.example.and_project_mbkm.data.local.datasource.UserLocalDataSource
import com.example.and_project_mbkm.data.local.datasource.UserLocalDataSourceImpl
import com.example.and_project_mbkm.data.network.datasource.AuthRemoteDataSource
import com.example.and_project_mbkm.data.network.datasource.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource
}