package com.sigarda.jurnalkas.model.usecase

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