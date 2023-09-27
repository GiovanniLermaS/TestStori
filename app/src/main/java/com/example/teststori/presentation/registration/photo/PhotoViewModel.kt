package com.example.teststori.presentation.registration.photo

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teststori.common.Resource
import com.example.teststori.domain.use_case.get_photo.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {

    private val _photoState = mutableStateOf(PhotoState())
    val photoState: State<PhotoState> = _photoState

    fun updateUserUri(uri: Uri?) {
        getPhotoUseCase(
            uri
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data.isNullOrBlank())
                        _photoState.value = PhotoState(isSuccess = "")
                    else _photoState.value = PhotoState(error = result.data)
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