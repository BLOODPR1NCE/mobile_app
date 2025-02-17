package com.example.mobil.Presentation.Navigate



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun NavHost(navController: NavHostController, startDestination: String, function: () -> Unit) {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = Routes.Load) {
                composable(Routes.Load)
                {
                    Load(navController)
                }
                composable(Routes.SignIn)
                {
                    SignIn(navController)
                }
                composable(Routes.SignUn)
                {
                    SignUp(navController)
                }
                composable(Routes.Home)
                {
                    Home(navController)
                }
            }
        }
    }
}

