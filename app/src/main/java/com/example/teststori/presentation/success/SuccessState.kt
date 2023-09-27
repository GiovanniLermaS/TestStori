package com.example.teststori.presentation.success

import com.google.firebase.auth.FirebaseUser

data class SuccessState(
    val isLoading: Boolean? = null,
    val user: FirebaseUser? = null,
    val error: String = ""
)