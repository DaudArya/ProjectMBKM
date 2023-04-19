package com.example.and_project_mbkm.ui.activity.login

import androidx.lifecycle.*
import com.example.and_project_mbkm.data.local.datastore.UserDataStore
import com.example.and_project_mbkm.data.local.preference.UserDataStoreManager
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginRequestBody
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginResponse
import com.example.and_project_mbkm.data.repository.AuthApiRepository
import com.example.and_project_mbkm.ui.fragment.setting.model.usecase.LoginUseCase
import com.example.and_project_mbkm.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
//    private val authRepository: AuthRepository,
    private val authRepository: AuthApiRepository,
    private val useCase: LoginUseCase,
    private val preferences: UserDataStore
) : ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginResponse>> get() = _postLoginUserResponse

    private var _state = MutableLiveData(com.example.and_project_mbkm.ui.activity.login.LoginState())
    val state: LiveData<com.example.and_project_mbkm.ui.activity.login.LoginState> = _state

    fun statusLogin(isLogin: Boolean) {
        viewModelScope.launch {
            dataStoreManager.statusLogin(isLogin)
        }
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return dataStoreManager.getLoginStatus().asLiveData()
    }

    fun SaveUserToken(isToken: String) {
        viewModelScope.launch {
            authRepository.SaveUserToken(isToken)
        }
    }

    fun getDataStoreToken(): LiveData<String> {
        return dataStoreManager.getToken.asLiveData()
    }

    fun login(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authRepository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            authRepository.setUserLogin(isLogin)
        }
    }

    fun setUserToken(isToken: String) {
        viewModelScope.launch {
            authRepository.setUserToken(isToken)
        }
    }

    fun getUserLoginStatus(): LiveData<Boolean> {
        return authRepository.getUserLoginStatus().asLiveData()
    }

    fun setUser(email: String) {
        preferences.login(email)
    }
}