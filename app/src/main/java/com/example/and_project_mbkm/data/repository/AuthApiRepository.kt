package com.example.and_project_mbkm.data.repository

import com.example.and_project_mbkm.data.local.datasource.UserLocalDataSource
import com.example.and_project_mbkm.data.network.datasource.AuthRemoteDataSource
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginRequestBody
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginResponse
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterRequestBody
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterResponse
import com.example.and_project_mbkm.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthApiRepository {
    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse>
    suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginResponse>
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setUserToken(isToken: String)
    suspend fun SaveUserToken(isToken: String)
    fun getUserLoginStatus(): Flow<Boolean>
}

class UserRepositoryImpl @Inject constructor(private val userLocalDataSource: UserLocalDataSource, private val dataSource: AuthRemoteDataSource) : AuthApiRepository {

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse> {
        return proceed {
            dataSource.postRegister(registerRequestBody)
        }
    }

    override suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginResponse> {
        return proceed {
            dataSource.postLogin(loginRequestBody)
        }
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        return userLocalDataSource.setUserLogin(isLogin)
    }

    override suspend fun setUserToken(isToken: String) {
        return userLocalDataSource.setUserToken(isToken)
    }

    override suspend fun SaveUserToken(isToken: String) {
        return userLocalDataSource.SaveUserToken(isToken)
    }

    override fun getUserLoginStatus(): Flow<Boolean> {
        return userLocalDataSource.getUserLoginStatus()
    }


    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e, e.message)
        }
    }

}