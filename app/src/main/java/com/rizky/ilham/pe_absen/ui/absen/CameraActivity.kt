package com.rizky.ilham.pe_absen.ui.absen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rizky.ilham.pe_absen.R
import kotlinx.android.synthetic.main.activity_camera.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class CameraActivity : AppCompatActivity() {
    private val cameraRequest = 1888
    lateinit var imageView: ImageView
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    //private lateinit var executor: Executor
    //private lateinit var biometricPrompt: BiometricPrompt
    //private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val url = "http://10.0.50.231:5001"
    private val POST = "POST"
    lateinit var photoFile: File
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        title = "Absensi"
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imageView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val nip = intent.getStringExtra("nip");
        button.setOnClickListener {
            dispatchTakePictureIntent()
            getCurrentLocation()
        }
        btn_kirim.setOnClickListener {
            val mimeType = photoFile?.let { getMimeType(it) }
            val fileName: String = photoFile.toString()
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
                fileName,
                photoFile.asRequestBody(mimeType?.toMediaTypeOrNull())
            )
        }
        /**
         *
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
         */
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    println("Error occurred while creating the File")
                    return@also
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
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
                        btn_kirim.visibility = View.VISIBLE
                    }
                    else{
                        radar.text = "kamu berada di luar area Rumah Sakit Harapan Anda. Tolong masuk lebih dalam ke area Rumah Sakit Harapan Anda"
                    }
                }
                else{
                    radar.text = "kamu berada di luar area Rumah Sakit Harapan Anda. Tolong masuk lebih dalam ke area Rumah Sakit Harapan Anda"
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
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
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
        imagename: String,
        imagevalue: RequestBody,
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
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart(nip!!, value1.toString())
                        .addFormDataPart(image!!,imagename,imagevalue)
                        .addFormDataPart(latitude!!,value2.toString())
                        .addFormDataPart(longitude!!,value3.toString())
                        .build()
                Request.Builder()
                    .url(fullURL)
                    .post(requestBody)
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

                    val nip: String? =  intent.getStringExtra("nip")
                    val nama: String? = intent.getStringExtra("nama")
                    val posisi: String? = intent.getStringExtra("posisi")
                    val gender: String? =intent.getStringExtra("gender")
                    val ttl: String? =intent.getStringExtra("ttl")
                    val email: String? = intent.getStringExtra("email")
                    val no_hp: String? =intent.getStringExtra("no_hp")
                    val alamat: String? = intent.getStringExtra("alamat")
                    if (Jobject["msg"].toString()== "kamu absen tepat waktu") {

                        val i = Intent(
                            this@CameraActivity as Context, AbsenSukses::class.java
                        )
                        i.putExtra("msg",Jobject["msg"].toString())
                        i.putExtra("nip",nip)
                        i.putExtra("nama",nama)
                        i.putExtra("posisi",posisi)
                        i.putExtra("gender",gender)
                        i.putExtra("ttl",ttl)
                        i.putExtra("email",email)
                        i.putExtra("no_hp",no_hp)
                        i.putExtra("alamat",alamat)
                        this@CameraActivity.startActivity(i)
                        this@CameraActivity.finish()
                    }
                    else{
                        val i = Intent(
                            this@CameraActivity as Context, AbsenGagal::class.java
                        )
                        i.putExtra("msg",Jobject["msg"].toString())
                        i.putExtra("nip",nip)
                        i.putExtra("nama",nama)
                        i.putExtra("posisi",posisi)
                        i.putExtra("gender",gender)
                        i.putExtra("ttl",ttl)
                        i.putExtra("email",email)
                        i.putExtra("no_hp",no_hp)
                        i.putExtra("alamat",alamat)
                        this@CameraActivity.startActivity(i)
                        this@CameraActivity.finish()
                    }
                }
            })
    }
}
