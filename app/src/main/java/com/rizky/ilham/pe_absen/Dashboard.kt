package com.rizky.ilham.pe_absen

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navArgument
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizky.ilham.pe_absen.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_login.*


class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_history, R.id.navigation_absen, R.id.navigation_profile
            )
        )
        navView.setupWithNavController(navController)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun getnip(): String? {
        val nip = intent.getStringExtra("nip");
        return nip
    }
    fun getnama(): String? {
        val nama = intent.getStringExtra("nama");
        return nama
    }
    fun getposisi(): String? {
        val posisi = intent.getStringExtra("posisi");
        return posisi
    }
    fun getgender(): String? {
        val gender = intent.getStringExtra("gender");
        return gender
    }
    fun getttl(): String? {
        val ttl = intent.getStringExtra("ttl");
        return ttl
    }
    fun getemail(): String? {
        val email = intent.getStringExtra("email");
        return email
    }
    fun getno_hp(): String? {
        val no_hp = intent.getStringExtra("no_hp");
        return no_hp
    }
    fun getalamat(): String? {
        val alamat = intent.getStringExtra("alamat");
        return alamat
    }
}