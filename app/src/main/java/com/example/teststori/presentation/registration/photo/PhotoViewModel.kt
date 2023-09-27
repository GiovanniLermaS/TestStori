package com.example.teststori.presentation.registration.photo

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teststori.common.Resource
import com.example.teststori.domain.use_case.get_photo.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {

    private val _photoState = MutableStateFlow(PhotoState())
    val photoState: StateFlow<PhotoState> = _photoState.asStateFlow()

    fun updateUserUri(uri: Uri?) {
        getPhotoUseCase(
            uri
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoState.value = PhotoState(isSuccess = result.data == true)
                }

                is Resource.Error -> {
                    _photoState.value = PhotoState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _photoState.value = PhotoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}