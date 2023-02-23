package com.example.and_project_mbkm.ui.activity.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.and_project_mbkm.data.local.datastore.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    preferences: UserDataStore
) : ViewModel() {
    val email = preferences.getEmail().asLiveData()
}