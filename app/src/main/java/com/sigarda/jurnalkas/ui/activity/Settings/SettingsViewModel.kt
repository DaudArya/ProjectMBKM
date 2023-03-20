package com.sigarda.jurnalkas.ui.activity.Settings

import androidx.lifecycle.ViewModel
import com.sigarda.jurnalkas.data.local.datastore.UserDataStore
import com.sigarda.jurnalkas.model.usecase.GetUserDataUseCase
import com.sigarda.jurnalkas.model.usecase.UpdateUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserDataUseCase,
    private val credentialUseCase: GetUserDataUseCase,
    private val preferences: UserDataStore
) : ViewModel() {
}