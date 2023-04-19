package com.example.and_project_mbkm.ui.activity.register

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.and_project_mbkm.data.local.preference.UserDataStoreManager
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterRequestBody
import com.example.and_project_mbkm.data.network.model.auth.register.RegisterResponse
import com.example.and_project_mbkm.data.repository.AuthApiRepository
import com.example.and_project_mbkm.ui.fragment.setting.model.User
import com.example.and_project_mbkm.ui.fragment.setting.model.usecase.RegisterUseCase
import com.example.and_project_mbkm.wrapper.Constant
import com.example.and_project_mbkm.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,
    private val useCase: RegisterUseCase
) : ViewModel() {

    private var _state = MutableLiveData(RegisterState())
    val state: LiveData<RegisterState> = _state

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

//    fun register(username: String, email: String, password: String, profilePhoto: Bitmap?) {
//        val user = User(
//            username = username,
//            email = email,
//            password = password,
//            profilePhoto = profilePhoto
//        )
//        useCase(user).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _state.value = RegisterState(user = result.data)
//                    Log.d(Constant.TAG, "Register ViewModel -> ${result.data}")
//                }
//                is Resource.Error -> {
//                    _state.value =
//                        RegisterState(
//                            error = result.message ?: "An unexpected error occured"
//                        )
//
//                    Log.d(Constant.TAG, "Error ViewModel -> ${result.message}")
//                }
//                is Resource.Loading -> {
//                    _state.value = RegisterState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
}