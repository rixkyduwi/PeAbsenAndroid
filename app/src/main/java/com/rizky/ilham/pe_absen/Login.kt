package com.rizky.ilham.pe_absen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rizky.ilham.pe_absen.api.POST
import com.rizky.ilham.pe_absen.api.endpointlogin
import com.rizky.ilham.pe_absen.api.url
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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
                    endpointlogin,
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
        endpoint: String,
        nip: String?,
        password: String?,
        value1: String?,
        value2: String?,
    ) {
        val respon: TextView = findViewById<View>(R.id.responlogin) as TextView
        /* if url is of our get request, it should not have parameters according to our implementation.
         * But our post request should have 'name' parameter. */
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

        /* this is how the callback get handled */
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    this@Login.runOnUiThread { respon.text = "api tidak merespon" }
                }
                override fun onResponse(call: Call, response: Response) {

                    // Read data on the worker thread
                    val jsonData: String = response.body!!.string()
                    val Jobject = JSONObject(jsonData)
                    // Run view-related code back on the main thread.
                    // Here we display the response message in our text view
                    if (Jobject["msg"].toString()== "login berhasil") {
                        this@Login.runOnUiThread { respon.text = "" }
                        val data = Jobject.getJSONArray("data")
                        val datalogin = JSONObject(data[0].toString())

                        val intent = Intent(this@Login , LoginSukses::class.java)

                        intent.putExtra("nip",datalogin["nip"].toString())
                        intent.putExtra("nama",datalogin["nama"].toString())
                        intent.putExtra("posisi",datalogin["posisi"].toString())
                        intent.putExtra("gender",datalogin["gender"].toString())
                        intent.putExtra("ttl",datalogin["ttl"].toString())
                        intent.putExtra("email",datalogin["email"].toString())
                        intent.putExtra("no_hp",datalogin["no_hp"].toString())
                        intent.putExtra("alamat",datalogin["alamat"].toString())
                        startActivity(intent)
                        finish()
                    } else {
                            this@Login.runOnUiThread { respon.text =Jobject["msg"].toString()}
                    }
                }
            })
    }
}