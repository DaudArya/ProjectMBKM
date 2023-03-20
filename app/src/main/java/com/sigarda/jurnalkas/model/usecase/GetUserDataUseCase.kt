package com.sigarda.jurnalkas.model.usecase

import javax.inject.Inject

class GetUserDataUseCase @Inject constructor()
//private val repository: AuthRepository
//) {
//    operator fun invoke(email: String): Flow<Resource<User>> = flow {
//        try {
//            emit(Resource.Loading())
//            val user = repository.getUserData(email).toUser()
//            emit(Resource.Success(user))
//        } catch (e: Exception) {
//
//        }
//    }
//}