package com.rizky.ilham.pe_absen.ui.absen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rizky.ilham.pe_absen.R
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.rizky.ilham.pe_absen.Dashboard
import kotlinx.android.synthetic.main.activity_absen_sukses.*

class AbsenSukses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_sukses)
        val image :ImageView= findViewById(R.id.tandacentang)
        val nip = intent.getStringExtra("nip");
        val nama = intent.getStringExtra("nama");
        val posisi = intent.getStringExtra("posisi");
        val gender = intent.getStringExtra("gender");
        val ttl = intent.getStringExtra("ttl");
        val email = intent.getStringExtra("email");
        val no_hp = intent.getStringExtra("no_hp");
        val alamat = intent.getStringExtra("alamat");
        val tanggal = intent.getStringExtra("tanggal");
        val waktu = intent.getStringExtra("waktu");
        texttanggal.text = "Behasil absen pada tanggal :\n"+tanggal
        textwaktu.text = waktu
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