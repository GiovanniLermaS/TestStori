package com.example.teststori.presentation.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teststori.R
import com.example.teststori.common.composables.Loader
import com.example.teststori.presentation.Screen

var n = 0
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    BackHandler(true) {}
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val visibilityIcon: ImageVector = if (isPasswordVisible) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Outlined.Visibility
    }
    val login = {
        if (email.isNotBlank() && password.isNotBlank())
            viewModel.loginUser(email, password)
    }
    val register = {
        navController.navigate(Screen.PersonalDataScreen.route)
    }
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state.isSuccess?.let {
                if (it.isBlank()) navController.navigate(Screen.HomeScreen.route)
                state.isSuccess = null
            }
            state.error?.let {
                Text(text = it)
                state.error = null
            }
            state.isLoading?.let {
                if (it) Loader()
                state.isLoading = null
            }
            TextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text(context.getString(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Box {
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text(context.getString(R.string.password)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                Icon(
                    imageVector = visibilityIcon,
                    contentDescription = context.getString(R.string.toggle_password_visibility),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(12.dp)
                        .clickable {
                            isPasswordVisible = !isPasswordVisible
                        }
                )
            }
            Button(onClick = login) {
                Text(context.getString(R.string.log_in))
            }
            Button(onClick = register) {
                Text(context.getString(R.string.register))
            }
        }
    }
}