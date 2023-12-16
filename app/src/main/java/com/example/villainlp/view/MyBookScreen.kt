package com.example.villainlp.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser

@Composable
fun MyBookScreen(user: FirebaseUser?, navController: NavHostController) {
    MyScaffold("내서재", navController) { ShowMyBooks(user, it, navController) }
}