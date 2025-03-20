package com.example.autogears.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Orders(
    @SerialName("id")
    val id: String,

    @SerialName("total_cost")
    val totalCost: Double,

    @SerialName("order_status_id")
    val orderStatusId: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("delivered_at")
    val deliveredAt: String,

    @SerialName("user_iduser_id")
    val userId: String,
)
