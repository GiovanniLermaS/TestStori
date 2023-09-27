package com.example.teststori.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teststori.R
import com.example.teststori.common.composables.Loader

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val value = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            value.balance?.let {
                Text(text = context.getString(R.string.balance))
                Text(text = "${it.balance}")
                value.balance = null
            }
            value.error?.let {
                Text(text = it)
                value.error = null
            }
            value.isLoading?.let {
                if (it) Loader()
                value.isLoading = null
            }
        }
    }
}