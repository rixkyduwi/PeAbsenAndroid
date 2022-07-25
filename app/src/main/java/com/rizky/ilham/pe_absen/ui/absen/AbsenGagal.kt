package com.rizky.ilham.pe_absen.ui.absen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rizky.ilham.pe_absen.Dashboard
import com.rizky.ilham.pe_absen.R
import kotlinx.android.synthetic.main.activity_absen_gagal.*

class AbsenGagal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_gagal)

        val msg = intent.getStringExtra("msg");
        val nip = intent.getStringExtra("nip");
        val nama = intent.getStringExtra("nama");
        val posisi = intent.getStringExtra("posisi");
        val gender = intent.getStringExtra("gender");
        val ttl = intent.getStringExtra("ttl");
        val email = intent.getStringExtra("email");
        val no_hp = intent.getStringExtra("no_hp");
        val alamat = intent.getStringExtra("alamat");
        textgagal.text=msg
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(
                this, Dashboard::class.java
            )
            i.putExtra("nip", nip)
            i.putExtra("nama", nama)
            i.putExtra("posisi", posisi)
            i.putExtra("gender", gender)
            i.putExtra("ttl", ttl)
            i.putExtra("email", email)
            i.putExtra("no_hp", no_hp)
            i.putExtra("alamat", alamat)
            startActivity(i)
            finish()
        }, 3000L)
    }
}