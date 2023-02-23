package com.example.and_project_mbkm.ui.activity.main

import androidx.lifecycle.ViewModel
import com.example.and_project_mbkm.data.local.dao.UserDao
import com.example.and_project_mbkm.data.local.datastore.UserDataStore
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