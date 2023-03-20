package com.sigarda.jurnalkas.ui.activity.main

import androidx.lifecycle.ViewModel
import com.sigarda.jurnalkas.data.local.datastore.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: UserDataStore
) : ViewModel() {

    fun logout() {
        preferences.logout()
    }
}