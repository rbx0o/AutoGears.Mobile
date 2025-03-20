package com.example.autogears.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SparePart(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("article_number")
    val articleNumber: String,

    @SerialName("purchase_price")
    val purchasePrice: Double,

    @SerialName("sale_price")
    val salePrice: Double,

    @SerialName("remains")
    val remains: Int,
)
