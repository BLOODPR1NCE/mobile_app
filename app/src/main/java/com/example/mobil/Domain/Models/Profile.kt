package com.example.mobil.Domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile (
    val username:String,
    val surname:String,
    @SerialName("datebirth") //Аннотация указывает, что при сериализации/десериализации это свойство должно соответствовать ключу "datebirth" в формате данных (например, JSON)
    val dateBirth:String?,
    val image:String?
)