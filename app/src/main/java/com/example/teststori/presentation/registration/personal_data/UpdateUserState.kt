package com.example.teststori.presentation.registration.personal_data

data class UpdateUserState(
    var isLoading: Boolean? = null,
    var isSuccess: Boolean? = null,
    var error: String? = null
)