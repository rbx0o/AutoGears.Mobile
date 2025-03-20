package com.example.autogears.viewmodels.mainviewmodels

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autogears.models.entities.Order
import com.example.autogears.models.entities.OrderContent
import com.example.autogears.models.entities.OrderStatus
import com.example.autogears.models.entities.SparePart
import com.example.autogears.services.Supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset

class OrderHistoryViewModel: ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            val userId = Supabase.supabase.auth.currentUserOrNull()?.id ?: return@launch

            val ordersResponse = Supabase.supabase.from("orders")
                .select{
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<Order>()

            val orders = ordersResponse.map { order ->
                val statusResponse = Supabase.supabase.from("order_status")
                    .select {
                        filter {
                            eq("id", order.orderStatusId)
                        }
                    }
                    .decodeSingle<OrderStatus>()

                Order(
                    id = order.id,
                    totalCost = order.totalCost,
                    status = statusResponse.name,
                    createdAt = order.createdAt,
                    orderStatusId = statusResponse.id,
                    deliveredAt = order.deliveredAt,
                    userId = order.userId
                )
            }

            _orders.value = orders
        }
    }

    fun generateInvoice(order: Order) {
        viewModelScope.launch {
            val orderContent = Supabase.supabase.from("order_content")
                .select {
                    filter {
                        eq("order_id", order.id)
                    }
                }.decodeList<OrderContent>()

            val spareParts = mutableListOf<String>()
            var totalSum = 0.0

            for (item in orderContent) {
                val sparePartId = item.sparePartId
                val quantity = item.quantityParts

                val part = Supabase.supabase.from("spare_parts")
                    .select {
                        filter {
                            eq("id", sparePartId)
                        }
                    }
                    .decodeSingle<SparePart>()

                val name = part.name
                val price = part.salePrice
                val sum = price * quantity

                spareParts.add("$name - $quantity шт. x $price = $sum руб.")
                totalSum += sum
            }

            val invoiceText = buildString {
                appendLine("Дата: ${order.createdAt}")
                appendLine()
                appendLine("Состав заказа:")
                spareParts.forEach { appendLine("• $it") }
                appendLine()
                appendLine("Итоговая стоимость: $totalSum руб.")
            }

            val fileName = "Invoice_${order.id}.txt"
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

            file.writeText(invoiceText, Charset.forName("UTF-8"))

            println("Чек сохранен: ${file.absolutePath}")
        }
    }
}