package com.example.and_project_mbkm.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.ActivitySplashBinding
import com.example.and_project_mbkm.ui.activity.login.LoginActivity
import com.example.and_project_mbkm.ui.activity.main.MainActivity
import com.example.and_project_mbkm.wrapper.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        startSplashScreen()

    }

    private fun startSplashScreen() {
        binding.logo.alpha = 0f
        binding.logo.animate().setDuration(1000).alpha(1f).withEndAction {
            checkCredential()
        }
    }

    private fun checkCredential() {
        viewModel.email.observe(this) { email ->
            if (email.isNotEmpty()) {
                Log.d(Constant.TAG, "email -> $email")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}