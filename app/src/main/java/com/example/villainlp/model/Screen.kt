package com.example.villainlp.model

sealed class Screen(val route: String) {
    object Login : Screen("LoginScreen")
    object Home : Screen("HomeScreen")
    object Logout : Screen("LogoutScreen")
    object CreativeYard : Screen("CreativeYardScreen")
    object Library : Screen("LibraryScreen")

    // Test 용
    object TestSendBookData : Screen("book")
}