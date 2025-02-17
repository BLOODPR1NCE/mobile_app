package com.example.mobil.Presentation.Navigate



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import java.lang.reflect.Modifier

@Composable
fun Navigation() {
    val NavigationFlag = rememberNavigationFlag()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Navigation(NavigationFlag = NavigationFlag, startDestination = Routes.Load) {
                composable(Routes.Load)
                {
                    SplashScreen(NavigationFlag)
                }
                composable(Routes.SignIn)
                {
                    SignInScreen(NavigationFlag)
                }
                composable(Routes.SignUn)
                {
                    SignUpScreen(NavigationFlag)
                }
                composable(Routes.Home)
                {
                    MainScreen(NavigationFlag)
                }
            }
        }
    }
}

