package com.rizky.ilham.pe_absen.ui.absen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rizky.ilham.pe_absen.R
import kotlinx.android.synthetic.main.activity_camera.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import kotlin.math.log


class CameraActivity : AppCompatActivity() {
    private val cameraRequest = 1888
    lateinit var imageView: ImageView
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val url = "http://10.0.50.130:5001"
    private val POST = "POST"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        title = "KotlinApp"
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imageView)
        val imagevalue: ImageView = imageView
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        button.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            getCurrentLocation()
            startActivityForResult(cameraIntent, cameraRequest)

        }
        btn_kirim.setOnClickListener {

            sendRequest(
                POST,
                "apiabsen",
                "nip",
                "latitude",
                "longitude",
                "image",
                nip,
                latitude.toString(),
                longitude.toString(),
                imagevalue
            )
            this@CameraActivity.startActivity(
                Intent(
                    this@CameraActivity as Context,
                    AbsenSukses::class.java
                )
            )
            finish()
        }
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errString == "Cancel") {
                        Toast.makeText(
                            applicationContext,
                            "Canceled", Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Authentication error: $errString", Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Anda Berhasil Absen!", Toast.LENGTH_SHORT)
                        .show()
                    this@CameraActivity.startActivity(Intent(applicationContext, AbsenSukses::class.java))
                    this@CameraActivity.finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Silahkan Masukan Sidik Jari Anda")
            .setSubtitle("error? Pengaturan > add Fingerprint")
            .setNegativeButtonText("Cancel")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        val biometricLoginButton =
            findViewById<Button>(R.id.btn_finger)
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }

    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
            return
        }
        fusedLocationClient.lastLocation

            .addOnSuccessListener { location ->
                // getting the last known or current location

                if ((location.latitude <= -6.875141 ) && (location.latitude>= -6.876490)){
                    if((location.longitude>=109.127974 ) && (location.longitude<= 109.129314)){
                        radar.text = "kamu berada di area Rumah Sakit Harapan Anda, Silahkan Absen"
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                    else{
                        radar.text = "kamu berada di luar area Rumah Sakit Harapan Anda. Tolong masuk lebih dalam ke area RUmah Sakit Harapan Anda"
                    }
                }
                else{
                    radar.text = "kamu berada di luar area Rumah Sakit Harapan Anda. Tolong masuk lebih dalam ke area RUmah Sakit Harapan Anda"
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(this, "Kamu Perlu Mengizinkan Akses Lokasi",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun sendRequest(
        method: String,
        endpoin: String,
        nip: String?,
        latitude: String?,
        longitude: String?,
        image: String?,
        value1: String?,
        value2: String?,
        value3: String?,
        imagevalue: ImageView,
    ) {
        /* if url is of our get request, it should not have parameters according to our implementation.
         * But our post request should have 'name' parameter. */
        val fullURL = "$url/$endpoin"
        val request: Request

        val client: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).build()

        /* If it is a post request, then we have to pass the parameters inside the request body*/request =
            if (method == POST) {
                val formBody: RequestBody = FormBody.Builder()
                    .add(nip!!, imagevalue.toString())
                    .add(latitude!!, imagevalue.toString())
                    .add(longitude!!, imagevalue.toString())
                    .add(image!!, imagevalue.toString())
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

        /* this is how the callback get handled */client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()

                }

                override fun onResponse(call: Call, response: Response) {

                    // Read data on the worker thread
                    val jsonData: String = response.body!!.string()
                    val Jobject = JSONObject(jsonData)
                    // Run view-related code back on the main thread.
                    // Here we display the response message in our text view

                    if (Jobject["msg"].toString()== "kamu absen tepat waktu") {

                        var i = Intent(
                            this@CameraActivity as Context, AbsenSukses::class.java
                        )
                        i.putExtra("msg",Jobject["msg"].toString())
                        this@CameraActivity.startActivity(i)
                        this@CameraActivity.finish()
                    } else if (Jobject["msg"].toString()== "kamu terlambat") {

                        var i = Intent(
                            this@CameraActivity as Context, AbsenGagal::class.java
                        )
                        i.putExtra("msg",Jobject["msg"].toString())
                        this@CameraActivity.startActivity(i)
                        this@CameraActivity.finish()
                    }else if (Jobject["msg"].toString()== "kamu absen terlalu cepat") {

                        var i = Intent(
                            this@CameraActivity as Context, AbsenGagal::class.java
                        )
                        i.putExtra("msg",Jobject["msg"].toString())
                        this@CameraActivity.startActivity(i)
                        this@CameraActivity.finish()
                    }else if (Jobject["msg"].toString()== "maaf kamu sudah absen") {

                        var i = Intent(
                            this@CameraActivity as Context, AbsenGagal::class.java
                        )
                        i.putExtra("msg",Jobject["msg"].toString())
                        this@CameraActivity.startActivity(i)
                        this@CameraActivity.finish()
                    }
                }
            })
    }

}