package com.example.autogears.views.mainviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autogears.viewmodels.mainviewmodels.OrderHistoryViewModel

@Composable
fun OrderHistoryScreen(viewModel: OrderHistoryViewModel = viewModel()) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("История заказов")
    }
}

@Preview
@Composable
private fun OrderHistoryScreenPreview() {
    OrderHistoryScreen()
}