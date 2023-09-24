package com.example.teststori.presentation.registration

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
) : ViewModel() {

    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state
}