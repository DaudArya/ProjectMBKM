package com.example.and_project_mbkm.data.network.datasource

import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginRequestBody
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginResponse
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterRequestBody
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterResponse
import com.example.and_project_mbkm.data.network.service.AuthApiInterface
import javax.inject.Inject

interface AuthRemoteDataSource {

    suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse
    suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiInterface) :
    AuthRemoteDataSource {

    override suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse {
        return apiService.postRegister(registerRequestBody)
    }
    override suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginResponse {
        return apiService.postLogin(loginRequestBody)
    }


}