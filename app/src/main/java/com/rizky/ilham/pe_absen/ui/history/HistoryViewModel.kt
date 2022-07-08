package com.rizky.ilham.pe_absen.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "History Absen"
    }
    val text: LiveData<String> = _text
}