package com.example.autogears.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Route(val route: String) {
    object AuthScreen: Route("auth_screen")
    object MainScreen: Route("main_screen")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.AuthScreen.route
    ) {
        composable(Route.AuthScreen.route) {
            AuthScreen(navController)
        }
        composable(Route.MainScreen.route) {
            MainScreen(navController)
        }
    }
}