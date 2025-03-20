package com.example.autogears.viewmodels.mainviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autogears.models.entities.SparePart
import com.example.autogears.models.queries.Get
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SparePartsViewModel: ViewModel() {
    private var _spareParts = MutableStateFlow<List<SparePart>>(emptyList())
    private var _defaultSpareParts = MutableStateFlow<List<SparePart>>(emptyList())
    var spareParts: StateFlow<List<SparePart>> = _spareParts.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _sortOrder = MutableStateFlow(Order.ASCENDING)

    init {
        fetchSpareParts()
    }

    private fun fetchSpareParts() {
        viewModelScope.launch {
            _spareParts.value = Get.SpareParts(_sortOrder)
            _defaultSpareParts.value = Get.SpareParts(_sortOrder)
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        _spareParts.value = _defaultSpareParts.value.filter {
            it.name.contains(query, ignoreCase = true) || it.articleNumber.contains(query, ignoreCase = true)
        }
    }

    fun sortByPrice(order: Order) {
        _sortOrder.value = order
        _spareParts.value = _spareParts.value.sortedBy { if (order == Order.ASCENDING) it.salePrice else -it.salePrice }
    }
}