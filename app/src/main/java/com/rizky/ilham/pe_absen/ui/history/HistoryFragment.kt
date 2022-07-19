package com.rizky.ilham.pe_absen.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizky.ilham.pe_absen.Dashboard
import com.rizky.ilham.pe_absen.LoginSukses
import com.rizky.ilham.pe_absen.databinding.FragmentHistoryBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val url = "http://10.0.49.16:5001"
    private val POST = "POST"
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val activity: Dashboard? = activity as Dashboard?
        val nip: String? = activity?.getnip()
        sendRequest(
            POST,
            "api/karyawan/history",
            "nip",
            nip,
        )

        val textView: TextView = binding.textHistory
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun sendRequest(
        method: String,
        endpoin: String,
        nip: String?,
        value1: Any?,
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
                    if (Jobject["msg"].toString()== "get history sukses") {
                        val data = Jobject.getJSONArray("data")

                    }
                    else {

                    }
                }
            })
    }

}