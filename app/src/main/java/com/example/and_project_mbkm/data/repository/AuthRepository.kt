package com.example.and_project_mbkm.data.repository

import com.example.and_project_mbkm.data.local.entity.UserEntity


interface AuthRepository {

    suspend fun login(email: String, password: String): UserEntity

    suspend fun register(user: UserEntity): Long

    suspend fun getUserData(email: String): UserEntity

    suspend fun updateUser(user: UserEntity): Int

}