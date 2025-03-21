package com.example.autogears.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatus(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,
)
