package com.sigarda.jurnalkas.ui.fragment.login

import androidx.lifecycle.*
import com.sigarda.jurnalkas.data.local.preference.UserDataStoreManager
import com.sigarda.jurnalkas.data.network.model.auth.login.email.LoginRequestBody
import com.sigarda.jurnalkas.data.network.model.auth.login.email.LoginResponse
import com.sigarda.jurnalkas.data.repository.AuthApiRepository
import com.sigarda.jurnalkas.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,
) : ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginResponse>> get() = _postLoginUserResponse


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

}