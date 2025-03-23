package com.example.mobil.Domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    @SerialName("category_id")
    val categoryId: Int,
    val description: String,
    val genre: String,
)
