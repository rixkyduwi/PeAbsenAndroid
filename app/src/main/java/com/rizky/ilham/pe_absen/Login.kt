package com.rizky.ilham.pe_absen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    private val url = "http://10.0.50.241:5001"
    private val POST = "POST"
    private val PUT = "PUT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)
        val register: Button = findViewById<View>(R.id.btnLogin) as Button
        val nip: TextView = findViewById<View>(R.id.nip) as TextView
        val password: TextView = findViewById<View>(R.id.pswd) as TextView
        val error: TextView = findViewById<View>(R.id.logineror) as TextView
        register.setOnClickListener {
            val txtnip: String = nip.text.toString()
            val txtpassword: String = password.text.toString()
            if (txtnip.isEmpty() && txtpassword.isEmpty()) {
                error.error = ""
                error.text = "harap isi semua"
            } else if (txtnip.isEmpty()) {
                error.error = ""
                error.text = "harap isi semua"
            } else if (txtpassword.isEmpty()) {
                error.error = ""
                error.text = "harap isi semua"
            } else {
                //if name text is not empty,then call the function to make the post request/
                sendRequest(
                    POST,
                    "apilogin",
                    "nip",
                    "password",
                    txtnip,
                    txtpassword
                )
            }
        }
    }

    private fun sendRequest(
        method: String,
        endpoin: String,
        nip: String?,
        password: String?,
        value1: String?,
        value2: String?,
    ) {
        val respon: TextView = findViewById<View>(R.id.responlogin) as TextView
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
                    .add(nip!!, value1.toString())
                    .add(password!!, value2.toString())
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
                    this@Login.runOnUiThread { respon.text = "api tidak merespon" }
                }

                override fun onResponse(call: Call, response: Response) {

                    // Read data on the worker thread
                    val responseData: String = response.body!!.string()
                    // Run view-related code back on the main thread.
                    // Here we display the response message in our text view
                    this@Login.runOnUiThread { respon.text = responseData }
                    if (responseData == "login berhasil") {
                        this@Login.runOnUiThread { respon.text = "" }

                        this@Login.startActivity(
                            Intent(
                                this@Login as Context, LoginSukses::class.java
                            )
                        )
                        this@Login.finish()
                    } else {
                        this@Login.runOnUiThread { respon.text = responseData }
                    }
                }
            })
    }
}