package com.example.teststori.presentation.registration.photo.composables

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.OutputStream

@Composable
fun bitmapImageToUri(bitmap: Bitmap): Uri? {
    val contentResolver = LocalContext.current.contentResolver

    val displayName = "${System.currentTimeMillis()}.jpg"

    val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, bitmap.width)
        put(MediaStore.Images.Media.HEIGHT, bitmap.height)
    }

    val imageUri = contentResolver.insert(imageCollection, contentValues)
    imageUri?.let { uri ->
        val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
            it.close()
        }
        return uri
    }
    return null
}