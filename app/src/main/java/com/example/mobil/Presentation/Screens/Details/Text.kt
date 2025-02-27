package com.example.mobil.Presentation.Screens.Details

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mobil.R

@Composable
fun TextDefault(value: String, onvaluechange: (String) -> Unit) {
    androidx.compose.material3.TextField(
        value = value,
        textStyle = MaterialTheme.typography.displayMedium,
        onValueChange = {
            onvaluechange(it)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray,
            unfocusedTextColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            focusedContainerColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun TextEmail(value: String, error: Boolean, onvaluechange: (String) -> Unit) {
    androidx.compose.material3.TextField(
        value = value,
        textStyle = MaterialTheme.typography.displayMedium,
        onValueChange = {
            onvaluechange(it)
        },
        isError = !error,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor =  Color.LightGray,
            unfocusedTextColor = Color.Black,
            focusedContainerColor =  Color.DarkGray,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorPlaceholderColor = Color.Red
        ),
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun TextPassword(value: String, onvaluechange: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    androidx.compose.material3.TextField(
        value = value,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                if (passwordVisibility) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_open),
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_close),
                        contentDescription = ""
                    )
                }
            }
        },
        onValueChange = { onvaluechange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.DarkGray,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent

        ),
        shape = RoundedCornerShape(20.dp),
    )
}