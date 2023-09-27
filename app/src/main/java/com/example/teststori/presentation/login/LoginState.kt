package com.example.teststori.presentation.login

data class LoginState(
    var isLoading: Boolean? = null,
    var isSuccess: String? = null,
    var error: String? = null
)