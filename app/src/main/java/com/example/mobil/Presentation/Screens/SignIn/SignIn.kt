package com.example.mobil.Presentation.Screens.SignIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobil.Presentation.Navigate.Routes
import com.example.mobil.Presentation.Screens.Details.TextEmail
import com.example.mobil.Presentation.Screens.Details.TextPassword
import com.example.mobil.R
import com.example.supabasesimpleproject.Domain.State.ResultState
import com.example.supabasesimpleproject.R
import com.example.supabasesimpleproject.Presentation.Screens.Components.ButtonNavigation
import com.example.supabasesimpleproject.Presentation.Screens.Components.TextFieldPassword
import com.example.supabasesimpleproject.Presentation.Screens.Components.TextFieldEmail
import com.example.supabasesimpleproject.Presentation.Navigation.NavigationRoutes

@Composable
fun SignInScreen(navController: NavHostController, signInViewModel: SignInViewModel = viewModel()) {

    val resultState by signInViewModel.resultState.collectAsState()
    val uiState = signInViewModel.uiState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextEmail(value = uiState.email, error = uiState.errorEmail,
            onvaluechange = { it -> signInViewModel.updateState(uiState.copy(email = it)) })
        Spacer(Modifier.height(10.dp))
        TextPassword(uiState.password) {
            signInViewModel.updateState(uiState.copy(password = it))
        }
        Spacer(Modifier.height(10.dp))

        when (resultState) {
            is ResultState.Error -> {
                ButtonNavigation(stringResource(R.string.name)) {
                    signInViewModel.signIn().toString()

                }
                Text((resultState as ResultState.Error).message)
            }
            is ResultState.Initialized -> {
                ButtonNavigation(stringResource(R.string.name)) {
                    signInViewModel.signIn().toString()
                }
            }
            ResultState.Loading -> {
                CircularProgressIndicator()
            }
            is ResultState.Success -> {
                navController.navigate(Routes.Home)
                {
                    popUpTo(Routes.SignIn) {
                        inclusive = true
                    }
                }
            }
        }

        Text(
            "Создать аккаунт",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.W600,
            modifier = Modifier.clickable {
                navController.navigate(Routes.SignUn)
            }
        )
    }
}

@Preview(locale = "es")
@Composable
fun PreviewSigIn() {
    SignInScreen(rememberNavController())
}