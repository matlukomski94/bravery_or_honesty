package com.mb.braveryorhonesty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mb.braveryorhonesty.ui.play.PlayScreen
import com.mb.braveryorhonesty.ui.StartScreen
import com.mb.braveryorhonesty.ui.category.CategoryScreen
import com.mb.braveryorhonesty.ui.players.PlayersListScreen
import com.mb.braveryorhonesty.ui.settings.SettingsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(
                onNavigateToPlayersList = { navController.navigate("players_list") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("category") {
            CategoryScreen(
                onCategorySelected = { categoryId ->
                    navController.navigate("play/$categoryId")
                },
                onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = "play/{categoryId}",
            arguments = listOf(navArgument("categoryId") { defaultValue = -1 })
        ) {
            PlayScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = "settings") {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = "players_list") {
            PlayersListScreen(
                onNavigateBack = { navController.popBackStack() },
                onStartGame = { navController.navigate("category") }
            )
        }
    }
}