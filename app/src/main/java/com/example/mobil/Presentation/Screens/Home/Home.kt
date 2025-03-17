package com.example.mobil.Presentation.Screens.Home

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mobil.Domain.Constant.supabase
import com.example.mobil.Domain.Сondition.SignUpCondititon
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.supabaseJson

@Composable
fun Home(navController: NavController) {
    val _uiState = mutableStateOf(SignUpCondititon())
    Text (
        text = "Привет"
    )
}