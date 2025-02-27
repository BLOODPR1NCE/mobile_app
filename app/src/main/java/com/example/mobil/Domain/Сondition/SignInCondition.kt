package com.example.mobil.Domain.Сondition

data class SignInCondition(
    val email: String = "",
    val password: String = "",
    var errorEmail: Boolean = false,
    val errorPassword: Boolean = false
)
