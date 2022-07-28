package com.rizky.ilham.pe_absen.ui.absen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rizky.ilham.pe_absen.Dashboard
import com.rizky.ilham.pe_absen.databinding.FragmentAbsenBinding
import java.util.*

class AbsenFragment : Fragment() {

    private var _binding: FragmentAbsenBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val absenViewModel =
            ViewModelProvider(this)[AbsenViewModel::class.java]

        _binding = FragmentAbsenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAbsen
        absenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val btnAbsen : Button= binding.btnAbsen
        val btnPulang : Button= binding.btnPulang
        btnAbsen.setOnClickListener{
            val i = Intent(context, Absen::class.java)
            i.putExtra("msg",btnAbsen.text.toString())
            this@AbsenFragment.startActivity(i)
        }
        btnPulang.setOnClickListener{
            val i = Intent(context, Absen::class.java)
            i.putExtra("msg",btnPulang.text.toString())
            this@AbsenFragment.startActivity(i)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}