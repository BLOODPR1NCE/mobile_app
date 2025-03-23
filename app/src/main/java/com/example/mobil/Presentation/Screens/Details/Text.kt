package com.example.mobil.Presentation.Screens.Details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobil.R

@Composable
fun TextDefault(value: String, onvaluechange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
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
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun TextEmail(value: String, error: Boolean, onvaluechange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun TextPassword(value: String, onvaluechange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun TextSearch(value: String, onvaluechange: (String) -> Unit) {
    androidx.compose.material3.TextField(
        value = value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = ""
            )
        },
        onValueChange = { onvaluechange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                "Поиск",
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        shape = RoundedCornerShape(15.dp),
    )
}