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

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        val activity: Dashboard? = activity as Dashboard?
        val nip: String? = activity?.getnip()
        val nama: String? = activity?.getnama()
        val posisi: String? = activity?.getposisi()
        val gender: String? = activity?.getgender()
        val ttl: String? = activity?.getttl()
        val email: String? = activity?.getemail()
        val no_hp: String? = activity?.getno_hp()
        val alamat: String? = activity?.getalamat()
        btnAbsen.setOnClickListener{
            val i = Intent(context, Absen::class.java)
            i.putExtra("nip",nip)
            i.putExtra("nama",nama)
            i.putExtra("posisi",posisi)
            i.putExtra("gender",gender)
            i.putExtra("ttl",ttl)
            i.putExtra("email",email)
            i.putExtra("no_hp",no_hp)
            i.putExtra("alamat",alamat)
            this@AbsenFragment.startActivity(i)
        }
        btnPulang.setOnClickListener{
            val i = Intent(context, AbsenPulang::class.java)
            i.putExtra("nip",nip)
            i.putExtra("nama",nama)
            i.putExtra("posisi",posisi)
            i.putExtra("gender",gender)
            i.putExtra("ttl",ttl)
            i.putExtra("email",email)
            i.putExtra("no_hp",no_hp)
            i.putExtra("alamat",alamat)
            this@AbsenFragment.startActivity(i)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}