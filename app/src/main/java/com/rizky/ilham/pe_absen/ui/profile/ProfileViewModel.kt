package com.rizky.ilham.pe_absen.ui.profile

import android.app.PendingIntent
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.ilham.pe_absen.Dashboard

class ProfileViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Profile"
    }
    val text: LiveData<String> = _text
}