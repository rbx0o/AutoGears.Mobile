package com.example.autogears.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.autogears.viewmodels.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autogears.R
import com.example.autogears.viewmodels.UserState
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    var flag = remember { mutableStateOf(false) }
    val userState by viewModel.userState
    var passwordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
                value = viewModel.emailUser,
                onValueChange = { viewModel.emailUser = it },
                label = {
                    Text("Email")
                }
            )
            TextField(
                value = viewModel.passwordUser,
                onValueChange = { viewModel.passwordUser = it },
                label = { Text("Пароль") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                trailingIcon = {
                    val bitmapIcon: ImageBitmap = if (passwordVisible) {
                        ImageBitmap.imageResource(R.drawable.view)
                    } else {
                        ImageBitmap.imageResource(R.drawable.no_view)
                    }
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(bitmap = bitmapIcon, contentDescription = null, tint = Color.Black)
                    }
                },
            )
            Button(
                onClick = {
                    viewModel.onSignInEmailPassword()
                    flag.value = true
                }
            ) {
                Text("Войти")
            }
        }
    }
    if (flag.value) {
        when (userState) {
            is UserState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UserState.Success -> {
                navController.navigate(Route.MainScreen.route)
                flag.value = false
            }
            is UserState.Error -> {
                Toast.makeText(context, "Email или пароль неверны", Toast.LENGTH_SHORT).show()
                flag.value = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    AuthScreen(rememberNavController())
}