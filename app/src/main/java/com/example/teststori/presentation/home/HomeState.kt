package com.example.teststori.presentation.home

import com.example.teststori.data.model.Balance

data class HomeState(
    var isLoading: Boolean? = null,
    var balance: Balance? = null,
    var error: String? = null
)