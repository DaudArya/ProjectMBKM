package com.sigarda.jurnalkas.ui.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigarda.jurnalkas.data.local.preference.UserDataStoreManager
import com.sigarda.jurnalkas.data.network.model.auth.register.RegisterRequestBody
import com.sigarda.jurnalkas.data.network.model.auth.register.RegisterResponse
import com.sigarda.jurnalkas.data.repository.AuthApiRepository
import com.sigarda.jurnalkas.model.usecase.RegisterUseCase
import com.sigarda.jurnalkas.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,
    private val useCase: RegisterUseCase
) : ViewModel() {


    private var _postRegisterUserResponse = MutableLiveData<Resource<RegisterResponse>>()
    val postRegisterUserResponse: LiveData<Resource<RegisterResponse>> get() = _postRegisterUserResponse


    fun postRegisterUser(registerRequestBody: RegisterRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val registerResponse = authRepository.postRegisterUser(registerRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postRegisterUserResponse.postValue(registerResponse)
            }
        }
    }
}