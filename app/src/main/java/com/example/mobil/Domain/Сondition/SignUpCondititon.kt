package com.example.mobil.Domain.Сondition

data class SignUpCondititon(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val username: String = "",
    val surname:String = "",
    val dateBirth: String? = null,
    var EmailError:Boolean = false
)
