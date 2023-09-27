package com.example.teststori.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teststori.common.Constants
import com.example.teststori.presentation.camera.CameraScreen
import com.example.teststori.presentation.detail.DetailScreen
import com.example.teststori.presentation.home.HomeScreen
import com.example.teststori.presentation.login.LoginScreen
import com.example.teststori.presentation.registration.personal_data.PersonalDataScreen
import com.example.teststori.presentation.registration.photo.PhotoScreen
import com.example.teststori.presentation.success.SuccessScreen
import com.example.teststori.presentation.ui.theme.TestStoriTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestStoriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ) {
                        composable(
                            route = Screen.PersonalDataScreen.route,
                        ) {
                            PersonalDataScreen(navController)
                        }
                        composable(
                            route = Screen.PhotoScreen.route,
                        ) {
                            PhotoScreen(navController)
                        }
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(navController)
                        }
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screen.CameraScreen.route
                        ) {
                            CameraScreen(navController)
                        }
                        composable(
                            route = Screen.SuccessScreen.route
                        ) {
                            SuccessScreen(navController)
                        }
                        composable(
                            route = Screen.DetailScreen.route + "/{${Constants.ID}}"
                        ) {
                            DetailScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestStoriTheme {
        Greeting("Android")
    }
}