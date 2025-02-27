package com.example.mobil.Presentation.Screens.SignIn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.Domain.Constant
import com.example.mobil.Domain.Utils.EmailValid
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Domain.Сondition.SignInCondition
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _uiCondition = mutableStateOf(SignInCondition())
    val uiCondition: SignInCondition get() = _uiCondition.value

    private val _resultCondition = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultCondition: StateFlow<ResultCondition> = _resultCondition.asStateFlow()

    fun UpdateCondition(newCondition: SignInCondition) {
        _uiCondition.value = newCondition
        _uiCondition.value.errorEmail = _uiCondition.value.email.EmailValid()
        _resultCondition.value = ResultCondition.Init
    }

    fun SignIn() {
        _resultCondition.value = ResultCondition.Loading
        if (_uiCondition.value.errorEmail) {
            viewModelScope.launch {
                try {
                    Constant.supabase.auth.signInWith(Email)
                    {
                        email = _uiCondition.value.email
                        password = _uiCondition.value.password
                    }

                    _resultCondition.value = ResultCondition.Success("Success")
                } catch (_ex: AuthRestException) {
                    _resultCondition.value = ResultCondition.Error(_ex.errorDescription ?: "Ошибка данных")
                }
            }
        } else {
            _resultCondition.value = ResultCondition.Error("Неправильно введена почта")
        }
    }
}