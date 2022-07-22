package com.rizky.ilham.pe_absen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navArgument
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizky.ilham.pe_absen.databinding.ActivityDashboardBinding


class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
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
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onparse(){
        if(intent.getStringExtra("nip") != null){
            val nip = intent.getStringExtra("nip");
        }
        if(intent.getStringExtra("nama") != null){
            val nama = intent.getStringExtra("nama");
        }
        if(intent.getStringExtra("posisi") != null){
            val posisi = intent.getStringExtra("posisi");
        }
        if(intent.getStringExtra("gender") != null){
            val gender = intent.getStringExtra("gender");
        }
        if(intent.getStringExtra("ttl") != null){
            val ttl = intent.getStringExtra("ttl");
        }
        if(intent.getStringExtra("email") != null){
            val email = intent.getStringExtra("email");
        }
        if(intent.getStringExtra("no_hp") != null){
            val no_hp = intent.getStringExtra("no_hp");
        }
        if(intent.getStringExtra("alamat") != null){
            val alamat = intent.getStringExtra("alamat");
        }
    }
}