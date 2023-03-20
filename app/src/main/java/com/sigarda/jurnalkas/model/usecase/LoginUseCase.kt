package com.sigarda.jurnalkas.model.usecase

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