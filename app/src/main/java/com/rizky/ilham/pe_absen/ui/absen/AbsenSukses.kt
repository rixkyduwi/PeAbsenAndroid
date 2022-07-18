package com.rizky.ilham.pe_absen.ui.absen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rizky.ilham.pe_absen.R
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.rizky.ilham.pe_absen.Dashboard
import kotlinx.android.synthetic.main.activity_absen_sukses.*

class AbsenSukses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_sukses)
        val msg = intent.getStringExtra("msg");
        val nip = intent.getStringExtra("nip");
        val nama = intent.getStringExtra("nama");
        val posisi = intent.getStringExtra("posisi");
        val gender = intent.getStringExtra("gender");
        val ttl = intent.getStringExtra("ttl");
        val email = intent.getStringExtra("email");
        val no_hp = intent.getStringExtra("no_hp");
        val alamat = intent.getStringExtra("alamat");
        this@AbsenSukses.runOnUiThread { textsukses.text = msg }
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(
                this@AbsenSukses as Context, Dashboard::class.java
            )
            i.putExtra("nip", nip)
            i.putExtra("nama", nama)
            i.putExtra("posisi", posisi)
            i.putExtra("gender", gender)
            i.putExtra("ttl", ttl)
            i.putExtra("email", email)
            i.putExtra("no_hp", no_hp)
            i.putExtra("alamat", alamat)
            this@AbsenSukses.startActivity(i)
            finish()
        }, 3000L)
    }
}