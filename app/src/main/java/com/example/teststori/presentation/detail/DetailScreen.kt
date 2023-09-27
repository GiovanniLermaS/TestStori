package com.example.teststori.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teststori.R
import com.example.teststori.common.composables.Loader
import com.example.teststori.common.convertIntToColombianMoney
import com.example.teststori.domain.model.Detail

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val value = viewModel.state.value
    val (error, onErrorChange) = remember { mutableStateOf("") }
    val (detail, onDetailChange) = remember { mutableStateOf<Detail?>(null) }
    value.details?.let {
        if (it.isNotEmpty()) onDetailChange(it[0])
        value.details = null
    }
    value.error?.let {
        onErrorChange(it)
        value.error = null
    }
    value.isLoading?.let {
        if (it) Loader()
        value.isLoading = null
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = error)
            Text(
                text = "${context.getString(R.string.costTransaction)}: ${
                    convertIntToColombianMoney(
                        detail?.ammount ?: 1
                    )
                }"
            )
            Text(text = "${context.getString(R.string.authorizationNumber)}: ${detail?.authorization}")
            Text(text = "${context.getString(R.string.origin)}: ${detail?.origin}")
            Text(text = "${context.getString(R.string.destination)}: ${detail?.destination}")
        }
    }
}