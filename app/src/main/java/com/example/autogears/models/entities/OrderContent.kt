package com.example.autogears.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderContent(
    @SerialName("id")
    val id: String,

    @SerialName("order_id")
    val orderId: String,

    @SerialName("spare_part_id")
    val sparePartId: String,

    @SerialName("quantity_parts")
    val quantityParts: Int,
)
