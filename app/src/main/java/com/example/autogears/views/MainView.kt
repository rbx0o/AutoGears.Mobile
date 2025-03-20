package com.example.autogears.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.autogears.viewmodels.AuthViewModel
import com.example.autogears.viewmodels.MainViewModel
import com.example.autogears.views.mainviews.SparePartsScreen
import com.example.autogears.views.mainviews.CartScreen
import com.example.autogears.views.mainviews.OrderHistoryScreen

sealed class MainScreenRoute(val route: String, val icon: ImageVector, val label: String) {
    data object SpareParts : MainScreenRoute("spare_parts", Icons.Default.Build, "Запчасти")
    data object Cart : MainScreenRoute("cart", Icons.Default.ShoppingCart, "Корзина")
    data object OrderHistory : MainScreenRoute("order_history", Icons.Default.Menu, "История")
}

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    var selectedScreen by rememberSaveable(stateSaver = mapSaver(
        save = { mapOf("route" to it.label) },
        restore = { label -> MainScreenRoute::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .firstOrNull { it.label == label["route"] } ?: MainScreenRoute.SpareParts
        }
    )) { mutableStateOf(MainScreenRoute.SpareParts) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(MainScreenRoute.SpareParts, MainScreenRoute.Cart, MainScreenRoute.OrderHistory)

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                is MainScreenRoute.SpareParts -> SparePartsScreen()
                is MainScreenRoute.Cart -> CartScreen()
                is MainScreenRoute.OrderHistory -> OrderHistoryScreen()
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(rememberNavController())
}