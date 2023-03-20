package com.sigarda.jurnalkas.ui.activity.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sigarda.jurnalkas.R
import com.sigarda.jurnalkas.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.hide()
    }

}