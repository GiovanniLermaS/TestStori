package com.example.teststori.presentation.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teststori.common.Resource
import com.example.teststori.domain.use_case.get_success.GetSuccessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val getSuccessUseCase: GetSuccessUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SuccessState())
    val state: StateFlow<SuccessState> = _state.asStateFlow()

    fun getUser() {
        getSuccessUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SuccessState(user = result.data)
                }

                is Resource.Error -> {
                    _state.value = SuccessState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = SuccessState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}