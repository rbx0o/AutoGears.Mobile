package com.example.autogears.views.mainviews

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autogears.models.entities.SparePart
import com.example.autogears.viewmodels.mainviewmodels.CartViewModel
import com.example.autogears.viewmodels.mainviewmodels.SparePartsViewModel
import io.github.jan.supabase.postgrest.query.Order

@Composable
fun SparePartsScreen(viewModel: SparePartsViewModel = viewModel(), cartViewModel: CartViewModel = viewModel()) {
    val spareParts by viewModel.spareParts.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var sortOrder by remember { mutableStateOf(Order.ASCENDING) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.search(it.text)
            },
            label = { Text("Поиск по названию или артикулу") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Button(
                onClick = {
                    sortOrder = if (sortOrder == Order.ASCENDING) Order.DESCENDING else Order.ASCENDING
                    viewModel.sortByPrice(sortOrder)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (sortOrder == Order.ASCENDING) "Сортировать по цене (↑)" else "Сортировать по цене (↓)")
            }
        }

        LazyColumn {
            items(spareParts) { part ->
                SparePartItem(part, onAddToCart = { cartViewModel.addToCart(it) })
            }
        }
    }
}

@Composable
fun SparePartItem(part: SparePart, onAddToCart: (SparePart) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = "Название: ${part.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Артикул: ${part.articleNumber}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Цена: ${part.salePrice} ₽", style = MaterialTheme.typography.bodyMedium)

            Button(
                onClick = {
                    onAddToCart(part)
                    Toast.makeText(context, "Товар был добавлен в корзину", Toast.LENGTH_SHORT).show()
                          },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Добавить в корзину")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SparePartsScreenPreview() {
    SparePartsScreen()
}