package com.rizky.ilham.pe_absen.ui.absen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rizky.ilham.pe_absen.R
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.rizky.ilham.pe_absen.Dashboard

class AbsenSukses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_sukses)
        Handler(Looper.getMainLooper()).postDelayed((Runnable {
            this.startActivity(
                Intent(
                    this as Context,
                    Dashboard::class.java
                )
            )
            finish()
        } as Runnable)!!, 3000L)
    }
}