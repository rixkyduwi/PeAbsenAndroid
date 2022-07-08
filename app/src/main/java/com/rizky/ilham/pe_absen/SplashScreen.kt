package com.rizky.ilham.pe_absen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Handler(Looper.getMainLooper()).postDelayed((Runnable {
            this@SplashScreen.startActivity(
                Intent(
                    this@SplashScreen as Context,
                    Login::class.java
                )
            )
            finish()
        } as Runnable)!!, 3000L)
    }
}