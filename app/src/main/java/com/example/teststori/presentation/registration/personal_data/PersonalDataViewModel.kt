package com.example.teststori.presentation.registration.personal_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teststori.common.Resource
import com.example.teststori.domain.use_case.get_personal_data.GetPersonalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val getPersonalDataUseCase: GetPersonalDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalDataState())
    val state: StateFlow<PersonalDataState> = _state.asStateFlow()

    private val _updateUserState = MutableStateFlow(UpdateUserState())
    val updateUserState: StateFlow<UpdateUserState> = _updateUserState.asStateFlow()

    fun registerUser(
        email: String,
        password: String
    ) {
        getPersonalDataUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PersonalDataState(isSuccess = result.data == true)
                }

                is Resource.Error -> {
                    _state.value = PersonalDataState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = PersonalDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateName(nameLastName: String) {
        getPersonalDataUseCase(nameLastName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _updateUserState.value = UpdateUserState(isSuccess = result.data == true)
                }

                is Resource.Error -> {
                    _updateUserState.value = UpdateUserState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _updateUserState.value = UpdateUserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}