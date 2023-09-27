package com.example.teststori.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teststori.common.Resource
import com.example.teststori.domain.use_case.get_home.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _stateMovements = mutableStateOf(MovementsState())
    val stateMovements: State<MovementsState> = _stateMovements

    init {
        getBalance()
    }

    private fun getBalance() {
        getHomeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null) {
                        _state.value = HomeState(error = "Balance es nulo")
                    } else {
                        getMovements()
                        _state.value = HomeState(balance = result.data)
                    }
                }

                is Resource.Error -> {
                    _state.value = HomeState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovements() {
        getHomeUseCase.getMovements().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null) {
                        _stateMovements.value = MovementsState(error = "No movimientos")
                    } else {
                        _stateMovements.value = MovementsState(movements = result.data)
                    }
                }

                is Resource.Error -> {
                    _stateMovements.value = MovementsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _stateMovements.value = MovementsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}