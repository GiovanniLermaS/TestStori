package com.example.teststori.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teststori.common.Constants
import com.example.teststori.common.Resource
import com.example.teststori.domain.use_case.get_detail.GetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.ID)?.let { id ->
            getDetail(id)
        }
    }

    private fun getDetail(id: String) {
        getDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DetailState(details = result.data)
                }

                is Resource.Error -> {
                    _state.value = DetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}