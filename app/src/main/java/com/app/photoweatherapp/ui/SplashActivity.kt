package com.app.photoweatherapp.ui

import android.content.Intent
import android.os.Bundle
import com.app.photoweatherapp.R
import com.app.photoweatherapp.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
