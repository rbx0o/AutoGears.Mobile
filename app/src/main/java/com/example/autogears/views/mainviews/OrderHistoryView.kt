package com.example.autogears.views.mainviews

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autogears.models.entities.Order
import com.example.autogears.viewmodels.mainviewmodels.OrderHistoryViewModel

@Composable
fun OrderHistoryScreen() {
    val viewModel: OrderHistoryViewModel = viewModel()
    val orders by viewModel.orders.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(orders) { order ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Дата заказа: ${order.createdAt}", fontWeight = FontWeight.Bold)
                    Text("Сумма: ${order.totalCost} руб.", fontWeight = FontWeight.Bold)
                    Text("Статус: ${order.status}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.generateInvoice(order) },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Сформировать чек")
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, context: Context, viewModel: OrderHistoryViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Дата: ${order.createdAt}", style = MaterialTheme.typography.bodyMedium)
            Text("Сумма: ${order.totalCost} руб.", style = MaterialTheme.typography.bodyMedium)
            Text("Статус: ${order.status}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.generateInvoice(order)
                    Toast.makeText(context, "Чек сохранен!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Сформировать чек")
            }
        }
    }
}

@Preview
@Composable
private fun OrderHistoryScreenPreview() {
    OrderHistoryScreen()
}