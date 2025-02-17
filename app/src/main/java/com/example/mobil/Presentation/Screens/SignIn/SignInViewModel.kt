package com.example.mobil.Presentation.Screens.SignIn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Domain.Сondition.SignInCondition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel : ViewModel() {
    private val _uiCondition = mutableStateOf(SignInCondition())
    val uiCondition: SignInCondition get() = _uiCondition.value
    
    private val _resultCondition = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultCondition: StateFlow<ResultCondition> = _resultCondition.asStateFlow()

    fun
}