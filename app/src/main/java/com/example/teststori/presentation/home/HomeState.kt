package com.example.teststori.presentation.home

import com.example.teststori.domain.model.Balance

data class HomeState(
    var isLoading: Boolean? = null,
    var balance: Balance? = null,
    var error: String? = null
)