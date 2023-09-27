package com.example.teststori.presentation.registration.photo

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teststori.R
import com.example.teststori.common.Constants
import com.example.teststori.common.composables.Loader
import com.example.teststori.presentation.Screen
import com.example.teststori.presentation.registration.composables.NoPermissionScreen
import com.example.teststori.presentation.registration.composables.bitmapImageToUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PhotoScreen(
    navController: NavController,
    viewModel: PhotoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionCamera = rememberPermissionState(Manifest.permission.CAMERA)
    val isPermissionCamera = checkPermissions(
        hasPermission = permissionCamera.status.isGranted,
        onRequestPermission = permissionCamera::launchPermissionRequest,
    )
    val (isNotSuccess, onNotSuccessChange) = remember { mutableStateOf(false) }
    val (error, onErrorChange) = remember { mutableStateOf("") }
    val (isLoading, onLoadingChange) = remember { mutableStateOf(false) }
    val bitmap =
        navController.currentBackStackEntry?.savedStateHandle?.get<Bitmap>(Constants.BITMAP)
    val uri = if (bitmap != null) {
        bitmapImageToUri(bitmap = bitmap)
    } else null
    val next = {
        if (uri != null)
            viewModel.updateUserUri(uri)
    }
    UpdateUriUser(
        viewModel = viewModel,
        navController = navController,
        onLoadingChange = onLoadingChange,
        onNotSuccessChange = onNotSuccessChange,
        onErrorChange = onErrorChange
    )
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isNotSuccess) Text(text = "Error actualizando uri")
            if (error.isNotBlank()) Text(text = error)
            if (isLoading) Loader()
            Card(modifier = Modifier.size(300.dp), shape = MaterialTheme.shapes.large,
                onClick = {
                    if (isPermissionCamera) navController.navigate(route = Screen.CameraScreen.route)
                }) {
                if (uri == null) {
                    Image(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Last captured photo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(model = uri),
                        contentDescription = "Last captured photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Text(text = context.getString(R.string.click_on_camera))
            Button(onClick = next) {
                Text(context.getString(R.string.next))
            }
        }
    }
}

@Composable
private fun checkPermissions(
    hasPermission: Boolean, onRequestPermission: () -> Unit
): Boolean {
    return if (hasPermission) {
        true
    } else {
        NoPermissionScreen(onRequestPermission)
        false
    }
}

@Composable
private fun UpdateUriUser(
    viewModel: PhotoViewModel,
    navController: NavController,
    onLoadingChange: (Boolean) -> Unit,
    onNotSuccessChange: (Boolean) -> Unit,
    onErrorChange: (String) -> Unit
) {
    LaunchedEffect(Dispatchers.IO) {
        viewModel.photoState.collect {
            onLoadingChange(false)
            it.isSuccess?.let { success ->
                if (success) {
                    navController.navigate(Screen.SuccessScreen.route)
                } else {
                    onNotSuccessChange(true)
                }
            }
            if (it.error.isNotBlank()) {
                onLoadingChange(false)
                onErrorChange(it.error)
            }
            it.isLoading?.let { loading ->
                if (loading) onLoadingChange(true)
            }
        }
    }
}
