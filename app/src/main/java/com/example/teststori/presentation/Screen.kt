package com.example.teststori.presentation

sealed class Screen(val route: String) {
    object PersonalDataScreen : Screen("registration_screen")
    object PhotoScreen : Screen("photo_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object CameraScreen : Screen("camera_screen")
    object SuccessScreen : Screen("success_screen")
}