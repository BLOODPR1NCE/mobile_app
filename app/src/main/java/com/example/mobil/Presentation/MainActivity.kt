package com.example.mobil.Presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mobil.Presentation.Navigate.MainNavHost
import com.example.mobil.Presentation.ui.theme.MobilTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobilTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainNavHost()
                }
            }
        }
    }
}