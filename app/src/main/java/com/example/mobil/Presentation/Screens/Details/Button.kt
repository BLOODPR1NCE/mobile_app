package com.example.mobil.Presentation.Screens.Details


import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonNavigate(label: String, onClick:()-> Unit, )
{
    Button (
        onClick = {
                onClick()
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Green
            ),
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
    ) {
        Text(
            label,
            fontSize = 15.sp,
            color = Color.White,
            fontWeight = FontWeight.W300
        )
    }
}
