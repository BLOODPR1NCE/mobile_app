package com.example.mobil.Domain.Сondition

data class BookCondition (
    var id :String = "",
    var title:String = "",
    var category:Int = -1,
    val description: String = "",
    val publication: String = ""
)