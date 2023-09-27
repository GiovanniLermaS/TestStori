package com.example.teststori.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teststori.R
import com.example.teststori.common.composables.Loader
import com.example.teststori.common.convertIntToColombianMoney
import com.example.teststori.domain.model.Movement
import com.example.teststori.presentation.home.composables.CardView

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    BackHandler(true) {}
    val context = LocalContext.current
    val value = viewModel.state.value
    val valueMovements = viewModel.stateMovements.value
    val lazyListState = rememberLazyListState()
    val (balance, onBalanceChange) = remember { mutableStateOf("") }
    val (movements, onMovementsChange) = remember { mutableStateOf(emptyList<Movement?>()) }
    val (error, onErrorChange) = remember { mutableStateOf("") }
    value.balance?.let {
        onBalanceChange(convertIntToColombianMoney(it.balance))
    }
    valueMovements.movements?.let {
        onMovementsChange(it)
    }
    value.isLoading?.let {
        if (it) Loader()
        value.isLoading = null
    }
    valueMovements.isLoading?.let {
        if (it) Loader()
        valueMovements.isLoading = null
    }
    value.error?.let {
        onErrorChange(it)
        value.error = null
    }
    valueMovements.error?.let {
        onErrorChange(it)
        valueMovements.error = null
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = error)
            Text(text = context.getString(R.string.balance))
            Text(text = balance)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                state = lazyListState,
            ) {
                items(movements) { movement ->
                    CardView(movement, navController)
                }
            }
        }
    }
}