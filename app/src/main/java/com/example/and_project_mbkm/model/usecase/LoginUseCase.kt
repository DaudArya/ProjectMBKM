package com.example.and_project_mbkm.model.usecase

import com.example.and_project_mbkm.data.local.entity.toUser
import com.example.and_project_mbkm.model.User
import com.example.and_project_mbkm.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor()
//    private val repository: AuthRepository
//) {
//    operator fun invoke(email: String, password: String): Flow<Resource<User>> = flow {
//        try {
//            emit(Resource.Loading())
//            val user = repository.login(email, password).toUser()
//            emit(Resource.Success(user))
//        } catch (e: Exception) {
//
//        }
//    }
//}