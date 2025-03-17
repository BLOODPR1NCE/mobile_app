package com.example.mobil.Presentation.Screens.SignUp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.Domain.Constant.supabase
import com.example.mobil.Domain.Models.Profile
import com.example.mobil.Domain.Utils.EmailValid
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Domain.Сondition.SignUpCondititon
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
    private val _uiState = mutableStateOf(SignUpCondititon())
    val uiState: SignUpCondititon get() = _uiState.value

    private val _resultState = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultState: StateFlow<ResultCondition> = _resultState.asStateFlow()

     fun UpdateState(newState: SignUpCondititon) {
        _uiState.value = newState
        _uiState.value.EmailError = _uiState.value.email.EmailValid()
        _resultState.value = ResultCondition.Init
    }

    fun SignUp()
    {
        _resultState.value = ResultCondition.Loading
        if (_uiState.value.EmailError && _uiState.value.password== _uiState.value.repeatPassword) {
            viewModelScope.launch {
                try {
                    supabase.auth.signUpWith(Email)
                    {
                        email = _uiState.value.email
                        password = _uiState.value.password
                    }
                    val user = Profile(_uiState.value.username,
                        _uiState.value.surname,
                        _uiState.value.dateBirth, null)
                    supabase.from("Profile").insert(user)
                    _resultState.value = ResultCondition.Success("Success")
                } catch (_ex: AuthRestException) {

                    _resultState.value = ResultCondition.Error(_ex.errorDescription ?: "Ошибка данных")
                }
            }
        }else{
            _resultState.value = ResultCondition.Error( "Ошибка ввода почты")
        }
    }
}