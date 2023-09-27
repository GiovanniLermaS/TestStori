package com.example.teststori.presentation.success

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teststori.R
import com.example.teststori.common.composables.Loader
import com.example.teststori.presentation.Screen
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SuccessScreen(
    navController: NavController,
    viewModel: SuccessViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val login = {
        navController.navigate(Screen.LoginScreen.route)
    }
    val (isNotSuccess, onNotSuccessChange) = remember { mutableStateOf(false) }
    val (error, onErrorChange) = remember { mutableStateOf("") }
    val (isLoading, onLoadingChange) = remember { mutableStateOf(false) }
    val (user, onFirebaseUserChange) = remember { mutableStateOf<FirebaseUser?>(null) }
    viewModel.getUser()
    User(
        viewModel = viewModel,
        onFirebaseUserChange = onFirebaseUserChange,
        onLoadingChange = onLoadingChange,
        onNotSuccessChange = onNotSuccessChange,
        onErrorChange = onErrorChange
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isNotSuccess) Text(text = "Error actualizando uri")
            if (error.isNotBlank()) Text(text = error)
            if (isLoading) Loader()
            Image(
                painter = rememberAsyncImagePainter(model = user?.photoUrl),
                contentDescription = "Last captured photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )
            Text(text = user?.displayName ?: "No name")
            Text(text = user?.email ?: "No email")
            Image(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                colorFilter = ColorFilter.tint(Color.Green)
            )
            Button(onClick = login) {
                Text(context.getString(R.string.log_in))
            }
        }
    }
}

@Composable
private fun User(
    viewModel: SuccessViewModel,
    onFirebaseUserChange: (FirebaseUser?) -> Unit,
    onLoadingChange: (Boolean) -> Unit,
    onNotSuccessChange: (Boolean) -> Unit,
    onErrorChange: (String) -> Unit
) {
    LaunchedEffect(Dispatchers.IO) {
        viewModel.state.collect {
            onLoadingChange(false)
            if (it.user == null) onNotSuccessChange(true)
            else {
                onFirebaseUserChange(it.user)
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