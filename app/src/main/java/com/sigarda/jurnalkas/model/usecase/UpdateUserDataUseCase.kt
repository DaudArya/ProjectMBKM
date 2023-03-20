package com.sigarda.jurnalkas.model.usecase

import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor()
//    private val repository: AuthRepository
//) {
//    operator fun invoke(user: User): Flow<Resource<Int>> = flow {
//        try {
//            emit(Resource.Loading())
//            val userEntity = user.toUserEntity()
//            val data = repository.updateUser(userEntity)
//            emit(Resource.Success(data))
//        } catch (e: Exception) {
//        }
//    }
//}