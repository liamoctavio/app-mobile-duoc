package com.example.sum1_b.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppViewModel : ViewModel() {
    private val _inputMode = MutableStateFlow("Escribir")
    val inputMode: StateFlow<String> = _inputMode

    fun setInputMode(mode: String) {
        _inputMode.value = mode
    }
}

