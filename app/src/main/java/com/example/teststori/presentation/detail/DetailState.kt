package com.example.teststori.presentation.detail

import com.example.teststori.domain.model.Detail

data class DetailState(
    var isLoading: Boolean? = null,
    var details: List<Detail?>? = null,
    var error: String? = null
)