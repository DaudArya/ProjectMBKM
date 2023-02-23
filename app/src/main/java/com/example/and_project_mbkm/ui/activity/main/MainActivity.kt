package com.example.and_project_mbkm.ui.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.ActivityMainBinding
import com.example.and_project_mbkm.ui.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logout()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this ,AuthActivity::class.java)
            startActivity(intent)
        }
    }
}