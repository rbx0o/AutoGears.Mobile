package com.example.autogears.models.queries

import android.util.Log
import com.example.autogears.models.entities.SparePart
import com.example.autogears.services.Supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.MutableStateFlow

object Get {
    suspend fun SpareParts(_sortOrder: MutableStateFlow<Order>): List<SparePart> {
        try {
            val parts = Supabase.supabase
                .from("spare_parts")
                .select() {
                    order("sale_price", _sortOrder.value)
                }
                .decodeList<SparePart>()

            return parts
        } catch (e: Exception) {
            Log.d("SpareParts", "${e.message}")
            return listOf()
        }
    }
}