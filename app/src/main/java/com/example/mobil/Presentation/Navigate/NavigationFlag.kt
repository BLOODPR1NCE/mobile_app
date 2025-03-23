package com.example.mobil.Presentation.Navigate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mobil.Presentation.Screens.Load.Loading
import com.example.mobil.Presentation.Screens.SignIn.SignInScreen
import com.example.mobil.Presentation.Screens.SignUp.SignUpScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.Load
            ) {
                composable(Routes.Load) {
                    Loading(navController)
                }
                composable(Routes.SignIn) {
                    SignInScreen(navController)
                }
                composable(Routes.SignUn) {
                    SignUpScreen(navController)
                }
                composable(Routes.Home) {
                    HomeScreen(navController)
                }
            }
        }
    }
}

