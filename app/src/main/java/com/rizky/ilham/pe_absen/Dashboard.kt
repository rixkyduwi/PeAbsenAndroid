package com.rizky.ilham.pe_absen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizky.ilham.pe_absen.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class Dashboard : AppCompatActivity() {
    private val url = "http://10.0.51.62:5001"
    private val POST = "POST"
    private lateinit var binding: ActivityDashboardBinding
    val endpoint = "api/karyawan/history"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val nip = intent.getStringExtra("nip");
        val a :TextView= binding.history
        sendRequest(POST,endpoint,"nip",nip.toString(),a)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration(
            setOf(
                R.id.navigation_history, R.id.navigation_absen, R.id.navigation_profile
            )
        )
        navView.setupWithNavController(navController)
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
    fun getttl(): String {
        val ttl = intent.getStringExtra("ttl")
        return ttl.toString()
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
    fun gethistory(): JSONArray {
        val nip = intent.getStringExtra("nip");
        val endpoint = "api/karyawan/history"
        sendRequest(POST,endpoint,"nip",nip.toString(),history)
        val history: TextView = findViewById<View>(R.id.history) as TextView
        println(history.text.toString())
        val Jobject = JSONObject(history.text.toString())
        return Jobject.getJSONArray("data")
    }
    private fun sendRequest(
        method: String,
        endpoint: String,
        nip: String?,
        value1: String?,
        history: TextView
    ) {
        val h:String = history.text.toString()
        val fullURL = "$url/$endpoint"
        val request: Request
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).build()

        /* If it is a post request, then we have to pass the parameters inside the request body*/request =
            if (method == POST) {
                val formBody: RequestBody = FormBody.Builder()
                    .add(nip!!, value1.toString())
                    .build()
                Request.Builder()
                    .url(fullURL)
                    .post(formBody)
                    .build()
            } else {
                //If it's our get request, it doen't require parameters, hence just sending with the url/
                Request.Builder()
                    .url(fullURL)
                    .build()
            }

        /* this is how the callback get handled */
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    this@Dashboard.runOnUiThread {
                            history.text = "api tidak merespon"

                    }
                }
                override fun onResponse(call: Call, response: Response) {

                    // Read data on the worker thread
                    val jsonData: String = response.body!!.string()
                    val Jobject = JSONObject(jsonData)
                    // Run view-related code back on the main thread.
                    // Here we display the response message in our text view
                    if (Jobject["msg"].toString()== "get history sukses") {
                        this@Dashboard.runOnUiThread {
                            history.text = jsonData
                        }
                    } else {
                        this@Dashboard.runOnUiThread {
                                history.text =Jobject["msg"].toString()
                            }
                        }
                    }
                })
            }
    }