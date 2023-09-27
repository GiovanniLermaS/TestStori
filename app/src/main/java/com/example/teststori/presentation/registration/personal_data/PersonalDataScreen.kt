package com.example.teststori.presentation.registration.personal_data

import android.annotation.SuppressLint
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PersonalDataScreen(
    navController: NavController, viewModel: PersonalDataViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val (name, onNameChange) = remember { mutableStateOf("") }
    val (lastName, onLastNameChange) = remember { mutableStateOf("") }
    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val value = viewModel.state.value
    val valueUpdate = viewModel.updateUserState.value
    val visibilityIcon: ImageVector = if (isPasswordVisible) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Outlined.Visibility
    }
    val next = {
        if (name != "" && lastName != "" && email != "" && password != "") viewModel.registerUser(
            email, password
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            value.isSuccess?.let {
                viewModel.updateName("$name $lastName")
                value.isSuccess = null
            }
            value.error?.let {
                Text(text = it)
                value.error = null
            }
            value.isLoading?.let {
                if (it) Loader()
                value.isLoading = null
            }
            valueUpdate.isSuccess?.let { success ->
                if (success) navController.navigate(Screen.PhotoScreen.route)
                valueUpdate.isSuccess = null
            }
            valueUpdate.error?.let {
                Text(text = it)
                valueUpdate.error = null
            }
            valueUpdate.isLoading?.let {
                if (it) Loader()
                valueUpdate.isLoading = null
            }
            TextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text(context.getString(R.string.name)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            TextField(
                value = lastName,
                onValueChange = onLastNameChange,
                label = { Text(context.getString(R.string.last_name)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
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
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                Icon(imageVector = visibilityIcon,
                    contentDescription = context.getString(R.string.toggle_password_visibility),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(12.dp)
                        .clickable {
                            isPasswordVisible = !isPasswordVisible
                        })
            }
            Button(onClick = next) {
                Text(context.getString(R.string.next))
            }
        }
    }
}