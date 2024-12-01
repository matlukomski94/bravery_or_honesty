package com.mb.braveryorhonesty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mb.braveryorhonesty.ui.PlayScreen
import com.mb.braveryorhonesty.ui.StartScreen
import com.mb.braveryorhonesty.ui.category.CategoryScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(
                onNavigateToCategory = { navController.navigate("category") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("category") {
            CategoryScreen(onCategorySelected = { categoryId ->
                navController.navigate("play/$categoryId")
            })
        }
        composable(
            route = "play/{categoryId}",
            arguments = listOf(navArgument("categoryId") { defaultValue = -1 })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: -1
            PlayScreen(categoryId = categoryId)
        }
    }
}