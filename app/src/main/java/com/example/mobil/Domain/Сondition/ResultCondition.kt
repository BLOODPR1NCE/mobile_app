package com.example.mobil.Domain.Сondition

import android.os.Message

sealed class  ResultCondition {
    data object Loading : ResultCondition()
    data object Init : ResultCondition()
    data class Success(val message: String) : ResultCondition()
    data class Error(val message: String) : ResultCondition()
}