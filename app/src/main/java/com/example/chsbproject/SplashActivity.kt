package com.example.chsbproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val shared: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        var users = shared.getString("users", "")

        Handler().postDelayed({
            var intent: Intent = if (users == "") {
                Intent(this, RegisterActivity::class.java)
            } else {
                Intent(this, CodeActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 5000)
    }
}