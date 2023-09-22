package com.example.teststori.presentation

sealed class Screen(val route: String) {
    object RegistrationScreen : Screen("registration_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
}