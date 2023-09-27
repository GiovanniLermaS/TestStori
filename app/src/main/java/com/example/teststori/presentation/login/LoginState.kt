package com.example.teststori.presentation.login

data class LoginState(
    val isLoading: Boolean? = null,
    val isSuccess: String? = null,
    val error: String = ""
)