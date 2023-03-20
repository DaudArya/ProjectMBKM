package com.sigarda.jurnalkas.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sigarda.jurnalkas.databinding.ActivitySplashBinding
import com.sigarda.jurnalkas.ui.activity.main.MainActivity
import com.sigarda.jurnalkas.ui.activity.auth.AuthActivity
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

        viewModel.getLoginStatus().observe(this) {
            if (it == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                   }
            else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

