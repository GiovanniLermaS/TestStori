package com.example.teststori.presentation.registration.photo

data class PhotoState(
    var isLoading: Boolean? = false,
    var isSuccess: String? = null,
    var error: String? = null
)