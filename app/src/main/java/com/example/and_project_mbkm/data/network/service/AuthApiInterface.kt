package com.example.and_project_mbkm.data.network.service

import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginRequestBody
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginResponse
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterRequestBody
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiInterface {
    @POST("register")
    suspend fun postRegister(
        @Body registerRequestBody: RegisterRequestBody
    ): RegisterResponse

    @POST("login")
    suspend fun postLogin(
        @Body loginRequestBody: LoginRequestBody
    ): LoginResponse
}