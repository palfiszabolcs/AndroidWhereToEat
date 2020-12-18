package com.example.wheretoeat.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretoeat.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val scaleToBig = AnimationUtils.loadAnimation(this, R.anim.scaletobig)
        val text = findViewById<TextView>(R.id.splash_text)
        text.startAnimation(scaleToBig)

        val SPLASH_TIME_OUT = 2000
        val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

}