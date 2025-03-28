package com.example.mobil.Domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile (
    val username:String,
    val surname:String,
    @SerialName("dateBirth")
    val dateBirth:String?,
    val image:String?
)