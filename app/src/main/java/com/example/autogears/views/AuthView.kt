package com.example.autogears.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.autogears.viewmodels.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun AuthScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .height(200.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                "Hello world",
                onValueChange = {},
                label = {
                    Text("Email")
                }
            )
            TextField(
                "Hello world",
                onValueChange = {},
                label = {
                    Text("Пароль")
                }
            )
            Button(
                onClick = {
                    try {
                        navController.navigate(Route.MainScreen.route)
                        Log.d("Navigation", "Success")
                    } catch (e: Exception)
                    {
                        Log.d("Navigation", "${e.message}")
                    }
                }
            ) {
                Text("Войти")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    AuthScreen(rememberNavController())
}