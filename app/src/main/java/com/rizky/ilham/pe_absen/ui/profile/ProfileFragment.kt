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
import kotlinx.android.synthetic.main.activity_camera.view.*

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
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        imageView = binding.avatar.imageView
        val activity: Dashboard? = activity as Dashboard?
        val nip: String? = activity?.getnip()
        val nama: String? = activity?.getnama()
        val posisi: String? = activity?.getposisi()
        val gender: String? = activity?.getgender()
        val ttl: String = activity!!.getttl()
        val email: String? = activity.getemail()
        val no_hp: String? = activity?.getno_hp()
        val alamat: String? = activity?.getalamat()
            binding.profileNip.text = "NIP : "+nip
            binding.profileNama.text = "Nama : "+nama
            binding.profilePswd.text = "Password : ********"
            binding.profilePosisi.text = "Posisi : "+posisi
            binding.profileGender.text = "Gender : "+gender
            binding.profileTtl.text = "Tanggal Lahir : "+ttl
            binding.profileEmail.text = "Email : "+email
            binding.profileNoHp.text = "No Hp : "+no_hp
            binding.profileAlamat.text = "Alamat : "+alamat
        val textView: TextView = binding.textProfile
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.profileEdit.setOnClickListener {
            val intent = Intent(context, EditProfile::class.java)
            intent.putExtra("nip",nip)
            intent.putExtra("nama",nama)
            intent.putExtra("posisi",posisi)
            intent.putExtra("gender",gender)
            intent.putExtra("ttl",ttl)
            intent.putExtra("email",email)
            intent.putExtra("no_hp",no_hp)
            intent.putExtra("alamat",alamat)
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