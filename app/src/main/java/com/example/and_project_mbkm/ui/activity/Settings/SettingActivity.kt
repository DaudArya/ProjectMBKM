package com.example.and_project_mbkm.ui.activity.Settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivitySettingBinding
    private val viewModel: com.example.and_project_mbkm.ui.activity.Settings.SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.setting_nav)
        setupActionBarWithNavController(navController)

        supportActionBar?.hide()
    }

}