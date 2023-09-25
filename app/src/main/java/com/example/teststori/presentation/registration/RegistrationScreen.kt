package com.example.teststori.presentation.registration

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teststori.R
import com.example.teststori.common.Constants
import com.example.teststori.presentation.Screen
import com.example.teststori.presentation.registration.composables.NoPermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val permissionCamera = rememberPermissionState(Manifest.permission.CAMERA)
    val isPermissionCamera = checkPermissions(
        hasPermission = permissionCamera.status.isGranted,
        onRequestPermission = permissionCamera::launchPermissionRequest,
    )
    val (name, onNameChange) = remember { mutableStateOf("") }
    val (lastName, onLastNameChange) = remember { mutableStateOf("") }
    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val (photo, onPhotoChange) = remember { mutableStateOf<File?>(null) }

    val visibilityIcon: ImageVector = if (isPasswordVisible) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Outlined.Visibility
    }

    val registrar = {
        if (photo != null) {
            //onRegistro(name, lastName, email, photo)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier
                    .size(128.dp)
                    .padding(16.dp), shape = MaterialTheme.shapes.large,
                onClick = {
                    if (isPermissionCamera)
                        navController.navigate(route = Screen.CameraScreen.route)
                }
            ) {
                val bitmap =
                    navController.currentBackStackEntry?.savedStateHandle?.get<Bitmap>(Constants.BITMAP)
                if (bitmap == null) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_photo),
                        contentDescription = "Last captured photo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    val capturedPhoto: ImageBitmap =
                        remember(bitmap.hashCode()) { bitmap.asImageBitmap() }
                    Image(
                        bitmap = capturedPhoto,
                        contentDescription = "Last captured photo",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            TextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            TextField(
                value = lastName,
                onValueChange = onLastNameChange,
                label = { Text("Apellido") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            TextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Box {
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                Icon(
                    imageVector = visibilityIcon,
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(12.dp)
                        .clickable {
                            isPasswordVisible = !isPasswordVisible
                        }
                )
            }
            Button(onClick = registrar) {
                Text("Registrar")
            }
        }
    }
}

@Composable
private fun checkPermissions(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
): Boolean {
    return if (hasPermission) {
        true
    } else {
        NoPermissionScreen(onRequestPermission)
        false
    }
}
