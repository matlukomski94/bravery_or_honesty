package com.mb.braveryorhonesty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mb.braveryorhonesty.ui.StartScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(
                onNavigateToPlay = { navController.navigate("play") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
    }
}