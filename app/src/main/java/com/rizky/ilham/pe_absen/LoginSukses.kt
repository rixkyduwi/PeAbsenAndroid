package com.rizky.ilham.pe_absen


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class LoginSukses: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login_sukses)
            Handler(Looper.getMainLooper()).postDelayed({
                this.startActivity(
                    Intent(
                        applicationContext,
                        Dashboard::class.java
                    )
                )
                finish()
            }, 3000L)
        }
}