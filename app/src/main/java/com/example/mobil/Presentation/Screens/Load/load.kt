package com.example.mobil.Presentation.Screens.Load

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mobil.Presentation.Navigate.Routes
import com.example.mobil.R
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Loading(navController: NavHostController) {
    val scale = remember {
        Animatable(1f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 3f,
            animationSpec = tween(
                durationMillis = 3000,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate(Routes.SignUn) {
            popUpTo(Routes.Load) {
                inclusive = true
            }
        }
    }

    BoxWithConstraints {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Logotip",
                modifier = Modifier.scale(scale.value)
            )
        }
    }
}