package com.rizky.ilham.pe_absen.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizky.ilham.pe_absen.Dashboard
import com.rizky.ilham.pe_absen.databinding.FragmentHistoryBinding
import org.json.JSONArray
import org.json.JSONObject


class HistoryFragment : Fragment(){
    private var _binding: FragmentHistoryBinding? = null

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
        val data: JSONArray?=activity?.gethistory()
        binding.recycleviewhistory.layoutManager= LinearLayoutManager(context)

        val datahistory = ArrayList<DetailHistory>()
        for (i in 0 until data?.length()!!) {
            if (i<=13){
                val history = JSONObject(data[i].toString())
                datahistory.add(DetailHistory(history["tanggal"].toString(), history["waktu"].toString(),history["status"].toString()))
            }
            else{
                null
            }

        }
        val adapter = HistoryAdapter(datahistory)
        binding.recycleviewhistory.adapter= adapter
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


}