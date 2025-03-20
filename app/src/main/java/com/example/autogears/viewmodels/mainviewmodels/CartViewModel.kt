package com.example.autogears.viewmodels.mainviewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autogears.models.entities.Order
import com.example.autogears.models.entities.OrderContent
import com.example.autogears.models.entities.SparePart
import com.example.autogears.services.Supabase
import com.example.autogears.services.Supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Serializable
data class CartItem(
    val sparePart: SparePart,
    var quantity: Int
)

class CartViewModel(): ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val totalCost: StateFlow<Double> = MutableStateFlow(0.0)

    fun addToCart(sparePart: SparePart) {
        val updatedList = _cartItems.value.toMutableList()
        val existingItem = updatedList.find { it.sparePart.id == sparePart.id }

        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            updatedList.add(CartItem(sparePart, 1))
        }

        _cartItems.value = updatedList
        recalculateTotal()
    }

    fun removeFromCart(sparePart: SparePart) {
        val updatedList = _cartItems.value.toMutableList()
        updatedList.removeAll { it.sparePart.id == sparePart.id }
        _cartItems.value = updatedList
        recalculateTotal()
    }

    private fun recalculateTotal() {
        totalCost as MutableStateFlow
        totalCost.value = _cartItems.value.sumOf { it.sparePart.salePrice * it.quantity }
    }

    fun placeOrder() {
        viewModelScope.launch {
            if (_cartItems.value.isEmpty()) return@launch

            val orderId = UUID.randomUUID().toString()
            val total = totalCost.value

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val current = ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(formatter)

            try {
                // Добавляем заказ
                supabase.from("orders").insert(
                    Order(
                        id = orderId,
                        totalCost = total,
                        orderStatusId = "91964990-cf5a-4a91-a439-cd70bdc59af3",
                        createdAt = current.toString(),
                        deliveredAt = null,
                        status = null,
                        userId = Supabase.supabase.auth.currentUserOrNull()?.id.toString()
                    )
                )

                // Добавляем состав заказа
                _cartItems.value.forEach { cartItem ->
                    supabase.from("order_content").insert(
                        OrderContent(
                            id = UUID.randomUUID().toString(),
                            orderId = orderId,
                            sparePartId = cartItem.sparePart.id,
                            quantityParts = cartItem.quantity
                        )
                    )
                }

                // Очищаем корзину
                _cartItems.value = emptyList()
                recalculateTotal()
                Log.d("PlaceOrder Success", "${System.currentTimeMillis()}")
            } catch (e: Exception) {
                Log.d("PlaceOrder Error", "${e.message}")
            }
        }
    }
}