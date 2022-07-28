package com.rizky.ilham.pe_absen.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rizky.ilham.pe_absen.Dashboard
import com.rizky.ilham.pe_absen.Login
import com.rizky.ilham.pe_absen.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var imageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val activity: Dashboard? = activity as Dashboard
            binding.profileNip.text = "NIP : "+activity?.getnip()
            binding.profileNama.text = "Nama : "+activity?.getnama()
            binding.profilePswd.text = "Password : ********"
            binding.profilePosisi.text = "Posisi : "+activity?.getposisi()
            binding.profileGender.text = "Gender : "+activity?.getgender()
            binding.profileTtl.text = "Tanggal Lahir : "+activity?.getttl()
            binding.profileEmail.text = "Email : "+activity?.getemail()
            binding.profileNoHp.text = "No Hp : "+activity?.getno_hp()
            binding.profileAlamat.text = "Alamat : "+activity?.getalamat()
        val textView: TextView = binding.textProfile
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.profileEdit.setOnClickListener {
            val intent = Intent(context, EditProfile::class.java)
            intent.putExtra("nip",activity?.getnip())
            intent.putExtra("nama",activity?.getnama())
            intent.putExtra("posisi",activity?.getposisi())
            intent.putExtra("gender",activity?.getgender())
            intent.putExtra("ttl",activity?.getttl())
            intent.putExtra("email",activity?.getemail())
            intent.putExtra("no_hp",activity?.getno_hp())
            intent.putExtra("alamat",activity?.getalamat())
            startActivity(intent)
        }
        binding.profileLogout.setOnClickListener {
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}