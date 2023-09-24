package com.example.teststori.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.teststori.common.Constants
import com.example.teststori.common.rotateBitmap
import com.example.teststori.presentation.Screen
import java.nio.ByteBuffer
import java.util.concurrent.Executor

@Composable
fun CameraScreen(
    navController: NavController, viewModel: CameraViewModel = hiltViewModel()
) {
    CameraContent(
        navController = navController,
        onPhotoCaptured = viewModel::storePhotoInGallery
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CameraContent(
    navController: NavController,
    onPhotoCaptured: (Bitmap) -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }
    cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text(text = "Take photo") },
            onClick = { capturePhoto(context, cameraController, onPhotoCaptured) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Camera capture icon"
                )
            })
    }) { paddingValues: PaddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                factory = { context ->
                    val previewView = PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                    previewView
                })

            viewModel.state.value.capturedImage?.let {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    Constants.BITMAP,
                    it
                )
                navController.popBackStack(
                    route = Screen.RegistrationScreen.route,
                    inclusive = false
                )
            }
        }
    }
}

private fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val bytes = getArrayUsingGetMethod(image.planes[0].buffer)
            val bitmap =
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, BitmapFactory.Options().apply {
                    inPreferredConfig = Bitmap.Config.ARGB_8888
                })
            onPhotoCaptured(bitmap.rotateBitmap(image.imageInfo.rotationDegrees))
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}

fun getArrayUsingGetMethod(byteBuffer: ByteBuffer): ByteArray {
    val bytes = ByteArray(byteBuffer.capacity())
    for (i in 0 until byteBuffer.capacity()) {
        bytes[i] = byteBuffer.get()
    }
    return bytes
}