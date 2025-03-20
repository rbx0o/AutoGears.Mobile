package com.example.autogears.views.mainviews

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autogears.viewmodels.mainviewmodels.CartItem
import com.example.autogears.viewmodels.mainviewmodels.CartViewModel

@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Корзина", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(cartItems) { cartItem ->
                CartItemRow(cartItem, onRemove = { viewModel.removeFromCart(it.sparePart) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Общая сумма: $totalCost ₽", style = MaterialTheme.typography.titleMedium)

        Button(
            onClick = {
                viewModel.placeOrder()
                Toast.makeText(context, "Заказ был успешно оформлен", Toast.LENGTH_SHORT).show()
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Заказать")
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onRemove: (CartItem) -> Unit) {
    val context = LocalContext.current

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Название: ${cartItem.sparePart.name}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Артикул: ${cartItem.sparePart.articleNumber}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Цена: ${cartItem.sparePart.salePrice} ₽", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Количество: ${cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            Button(
                onClick = {
                    onRemove(cartItem)
                    Toast.makeText(context, "Товар был удален из корзины", Toast.LENGTH_SHORT).show()
                          },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Удалить")
            }
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen()
}