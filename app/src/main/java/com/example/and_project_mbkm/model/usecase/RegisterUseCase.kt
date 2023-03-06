package com.example.and_project_mbkm.model.usecase

import com.example.and_project_mbkm.model.User
import com.example.and_project_mbkm.model.toUserEntity
import com.example.and_project_mbkm.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor()
//    private val repository: AuthRepository
//) {
//    operator fun invoke(user: User): Flow<Resource<Long>> = flow {
//        try {
//            emit(Resource.Loading())
//            val userEntity = user.toUserEntity()
//            val data = repository.register(userEntity)
//            emit(Resource.Success(data))
//        } catch (e: Exception) {
//
//        }
//    }
//}