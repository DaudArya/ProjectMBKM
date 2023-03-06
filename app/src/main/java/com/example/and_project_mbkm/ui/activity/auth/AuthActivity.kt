package com.example.and_project_mbkm.ui.activity.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.and_project_mbkm.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()
    }
}