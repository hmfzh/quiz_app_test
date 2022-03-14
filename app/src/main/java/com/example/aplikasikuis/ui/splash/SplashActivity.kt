package com.example.aplikasikuis.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.aplikasikuis.R
import com.example.aplikasikuis.ui.main.MainActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        spalsh()
    }

    private fun spalsh() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<MainActivity>()
        },2000)
    }
}