package com.example.mobil.Presentation.Screens.SignIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Presentation.Navigate.Routes
import com.example.mobil.Presentation.Screens.Details.ButtonNavigate
import com.example.mobil.Presentation.Screens.Details.TextEmail
import com.example.mobil.Presentation.Screens.Details.TextPassword

@Composable
fun SignInScreen(navController: NavHostController, signInViewModel: SignInViewModel = viewModel()) {
    val resultState by signInViewModel.resultCondition.collectAsState()
    val uiState = signInViewModel.uiCondition

    SignInForm(
        email = uiState.email,
        password = uiState.password,
        errorEmail = uiState.errorEmail,
        onEmailChange = { signInViewModel.UpdateCondition(uiState.copy(email = it)) },
        onPasswordChange = { signInViewModel.UpdateCondition(uiState.copy(password = it)) },
        onSignInClick = { signInViewModel.SignIn() },
        isLoading = resultState is ResultCondition.Loading,
        errorMessage = (resultState as? ResultCondition.Error)?.message,
        onCreateAccountClick = { navController.navigate(Routes.SignUn) }
    )

    if (resultState is ResultCondition.Success) {
        LaunchedEffect(Unit) {
            navController.navigate(Routes.Home) {
                popUpTo(Routes.SignIn) { inclusive = true }
            }
        }
    }
}

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    errorEmail: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onCreateAccountClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextEmail(email, errorEmail, onEmailChange)
        Spacer(Modifier.height(10.dp))
        TextPassword(password, onPasswordChange)
        Spacer(Modifier.height(10.dp))
        ButtonNavigate(
            label = "Войти",
            onClick = onSignInClick
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
        }


        Text(
            text = "Создать аккаунт",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.W600,
            modifier = Modifier.clickable(onClick = onCreateAccountClick)
        )
    }
}

@Preview(locale = "es")
@Composable
fun PreviewSigIn() {
    SignInScreen(rememberNavController())
}