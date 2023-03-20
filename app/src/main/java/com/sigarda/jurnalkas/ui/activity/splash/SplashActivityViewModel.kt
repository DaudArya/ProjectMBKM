package com.sigarda.jurnalkas.ui.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sigarda.jurnalkas.data.local.datastore.UserDataStore
import com.sigarda.jurnalkas.data.local.preference.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    preferences: UserDataStore,
    private val dataStoreManager: UserDataStoreManager
) : ViewModel() {
    val email = dataStoreManager.getEmail().asLiveData()

    fun getLoginStatus(): LiveData<Boolean> {
        return dataStoreManager.getLoginStatus().asLiveData()
    }
}