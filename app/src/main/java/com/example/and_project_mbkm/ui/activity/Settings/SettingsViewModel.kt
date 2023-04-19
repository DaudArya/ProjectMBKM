package com.example.and_project_mbkm.ui.activity.Settings

import androidx.lifecycle.ViewModel
import com.example.and_project_mbkm.data.local.datastore.UserDataStore
import com.example.and_project_mbkm.ui.fragment.setting.model.usecase.GetUserDataUseCase
import com.example.and_project_mbkm.ui.fragment.setting.model.usecase.UpdateUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserDataUseCase,
    private val credentialUseCase: GetUserDataUseCase,
    private val preferences: UserDataStore
) : ViewModel() {
}