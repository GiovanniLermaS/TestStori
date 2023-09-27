package com.example.teststori.presentation.home

import com.example.teststori.domain.model.Movement

data class MovementsState(
    var isLoading: Boolean? = null,
    var movements: List<Movement?>? = null,
    var error: String? = null
)