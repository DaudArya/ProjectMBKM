package com.example.and_project_mbkm.ui.activity.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.ActivityMainBinding
import com.example.and_project_mbkm.databinding.ActivitySettingBinding
import com.example.and_project_mbkm.ui.activity.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}