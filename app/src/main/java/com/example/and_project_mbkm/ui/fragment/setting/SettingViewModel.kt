package com.example.and_project_mbkm.ui.fragment.setting

import android.util.Log
import androidx.lifecycle.*
import com.example.and_project_mbkm.data.local.datastore.UserDataStore
import com.example.and_project_mbkm.data.local.preference.UserDataStoreManager
import com.example.and_project_mbkm.ui.fragment.setting.model.User
import com.example.and_project_mbkm.ui.fragment.setting.model.usecase.GetUserDataUseCase
import com.example.and_project_mbkm.ui.fragment.setting.model.usecase.UpdateUserDataUseCase
import com.example.and_project_mbkm.wrapper.Constant
import com.example.and_project_mbkm.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val dataStoreManager: UserDataStoreManager,
    private val updateUserUseCase: UpdateUserDataUseCase,
    private val credentialUseCase: GetUserDataUseCase,
    private val preferences: UserDataStore
) : ViewModel() {

    private val _userData = MutableLiveData(ProfileState())
    val userData: LiveData<ProfileState> = _userData

    private val _updateResult = MutableLiveData(ProfileState())
    val updateResult: LiveData<ProfileState> = _updateResult

    val email = preferences.getEmail().asLiveData()

    fun statusLogin(isLogin: Boolean) {
        viewModelScope.launch {
            dataStoreManager.statusLogin(isLogin)
        }
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return dataStoreManager.getLoginStatus().asLiveData()
    }


    fun logout() {
        preferences.logout()
    }
}