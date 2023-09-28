package com.example.teststori.presentation.login

import androidx.activity.compose.BackHandler
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

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    BackHandler(true) {}
    val context = LocalContext.current

    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (isEmailValid, onEmailValidChange) = remember { mutableStateOf(true) }
    val (isEmailEmpty, onEmailEmptyChange) = remember { mutableStateOf(false) }

    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val (isPasswordEmpty, onPasswordEmptyChange) = remember { mutableStateOf(false) }
    val (isPasswordVisible, onPasswordVisibleChange) = remember { mutableStateOf(false) }

    val login = {
        if (email.isNotEmpty() && isEmailValid && password.isNotEmpty()
        ) viewModel.loginUser(email, password)
        if (email.isEmpty()) onEmailEmptyChange(true)
        if (password.isEmpty()) onPasswordEmptyChange(true)
    }
    val register = {
        navController.navigate(Screen.PersonalDataScreen.route)
    }
    val state = viewModel.state.value
    state.isSuccess?.let {
        if (it.isBlank()) navController.navigate(Screen.HomeScreen.route)
        state.isSuccess = null
    }
    state.isLoading?.let {
        if (it) Loader()
        state.isLoading = null
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state.error?.let {
                Text(text = it)
                state.error = null
            }
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
            Button(onClick = login) {
                Text(context.getString(R.string.log_in))
            }
            Button(onClick = register) {
                Text(context.getString(R.string.register))
            }
        }
    }
}