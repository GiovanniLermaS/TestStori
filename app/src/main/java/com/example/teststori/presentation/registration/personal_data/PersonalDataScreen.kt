package com.example.teststori.presentation.registration.personal_data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teststori.R
import com.example.teststori.common.composables.Email
import com.example.teststori.common.composables.Loader
import com.example.teststori.common.composables.Password
import com.example.teststori.presentation.Screen
import com.example.teststori.presentation.registration.personal_data.composables.TextF

@Composable
fun PersonalDataScreen(
    navController: NavController, viewModel: PersonalDataViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val (name, onNameChange) = remember { mutableStateOf("") }
    val (isNameEmpty, onNameEmptyChange) = remember { mutableStateOf(false) }

    val (lastName, onLastNameChange) = remember { mutableStateOf("") }
    val (isLastNameEmpty, onLastNameEmptyChange) = remember { mutableStateOf(false) }

    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (isEmailValid, onEmailValidChange) = remember { mutableStateOf(true) }
    val (isEmailEmpty, onEmailEmptyChange) = remember { mutableStateOf(false) }

    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val (isPasswordEmpty, onPasswordEmptyChange) = remember { mutableStateOf(false) }
    val (isPasswordVisible, onPasswordVisibleChange) = remember { mutableStateOf(false) }

    val value = viewModel.state.value
    val valueUpdate = viewModel.updateUserState.value
    val next = {
        if (name.isNotEmpty() &&
            lastName.isNotEmpty() &&
            email.isNotEmpty() &&
            isEmailValid &&
            password.isNotEmpty()
        ) viewModel.registerUser(
            email,
            password
        )
        if (name.isEmpty()) onNameEmptyChange(true)
        if (lastName.isEmpty()) onLastNameEmptyChange(true)
        if (email.isEmpty()) onEmailEmptyChange(true)
        if (password.isEmpty()) onPasswordEmptyChange(true)
    }
    value.isSuccess?.let {
        viewModel.updateName("$name $lastName")
        value.isSuccess = null
    }
    value.isLoading?.let {
        if (it) Loader()
        value.isLoading = null
    }
    valueUpdate.isSuccess?.let { success ->
        if (success) navController.navigate(Screen.PhotoScreen.route)
        valueUpdate.isSuccess = null
    }
    valueUpdate.isLoading?.let {
        if (it) Loader()
        valueUpdate.isLoading = null
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            value.error?.let {
                Text(text = it)
                value.error = null
            }
            valueUpdate.error?.let {
                Text(text = it)
                valueUpdate.error = null
            }
            TextF(
                name = name,
                onNameChange = onNameChange,
                isEmptyField = isNameEmpty,
                onEmptyFieldChange = onNameEmptyChange,
                text = context.getString(R.string.name)
            )
            TextF(
                name = lastName,
                onNameChange = onLastNameChange,
                isEmptyField = isLastNameEmpty,
                onEmptyFieldChange = onLastNameEmptyChange,
                text = context.getString(R.string.last_name)
            )
            Email(
                email = email,
                onEmailChange = onEmailChange,
                isEmailValid = isEmailValid,
                onEmailValidChange = onEmailValidChange,
                isEmailEmpty = isEmailEmpty,
                onEmailEmptyChange = onEmailEmptyChange
            )
            Password(
                password = password,
                onPasswordChange = onPasswordChange,
                isPasswordEmpty = isPasswordEmpty,
                onPasswordEmptyChange = onPasswordEmptyChange,
                isPasswordVisible = isPasswordVisible,
                onPasswordVisibleChange = onPasswordVisibleChange
            )
            Button(onClick = next) {
                Text(context.getString(R.string.next))
            }
        }
    }
}