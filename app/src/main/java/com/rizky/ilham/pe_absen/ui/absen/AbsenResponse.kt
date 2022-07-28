package com.rizky.ilham.pe_absen.ui.absen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rizky.ilham.pe_absen.R
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import com.rizky.ilham.pe_absen.Dashboard
import kotlinx.android.synthetic.main.activity_absen_response.*

class AbsenResponse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_response)
        val nip = intent.getStringExtra("nip")
        val nama = intent.getStringExtra("nama")
        val posisi = intent.getStringExtra("posisi")
        val gender = intent.getStringExtra("gender")
        val ttl = intent.getStringExtra("ttl")
        val email = intent.getStringExtra("email")
        val no_hp = intent.getStringExtra("no_hp")
        val alamat = intent.getStringExtra("alamat")
        val tanggal = intent.getStringExtra("tanggal")
        val waktu = intent.getStringExtra("waktu")
        val msg = intent.getStringExtra("msg")
        if (msg=="kamu absen tepat waktu") {
            tanda.setImageResource(R.drawable.tandacentang)
            texttanggal.text = "Behasil absen pada tanggal :\n" + tanggal
            textwaktu.text = waktu
        }
        else{
            tanda.setImageResource(R.drawable.tandasilang)
            texttanggal.visibility = View.INVISIBLE
            textwaktu.visibility = View.INVISIBLE
            text1.visibility = View.INVISIBLE
            textgagal.visibility = View.VISIBLE
            textgagal.text = msg
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(
                this , Dashboard::class.java
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