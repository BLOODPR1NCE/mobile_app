package com.example.mobil.Presentation.Screens.SignUp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Presentation.Navigate.Routes
import com.example.mobil.Presentation.Screens.Details.ButtonNavigate
import com.example.mobil.Presentation.Screens.Details.TextDropDown
import com.example.mobil.Presentation.Screens.Details.TextEdit
import com.example.mobil.R

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreateBookScreen(navController: NavHostController, createBookViewModel: CreateBookViewModel = viewModel()) {

    var imageUrl by remember { mutableStateOf("") }
    val bookState = createBookViewModel.state
    val resultState by createBookViewModel.resultState.collectAsState()
    val context = LocalContext.current
    var selectedImageUri  by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedImageUri = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Добавление книги",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.W600
        )

        Spacer(modifier = Modifier.height(16.dp))

        val imageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                .size(Size.ORIGINAL)
                .build()
        ).state

        LaunchedEffect(bookState) {
            imageUrl = createBookViewModel.getUrlImage(bookState.id)
        }
        if (imageState is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (imageState is AsyncImagePainter.State.Error) {
            Image(

                painter = painterResource(R.drawable.book),
                contentDescription = bookState.title,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp)),

                contentScale = ContentScale.Crop
            )
        }

        if (imageState is AsyncImagePainter.State.Success) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(

                    painter = imageState.painter,
                    contentDescription = bookState.title,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }


        Spacer(modifier = Modifier.width(16.dp))

                TextEdit(
                    value = bookState.title,
                    label = "Название",
                    placeholder = "Введите название книги",
                    onValueChanged = { createBookViewModel.updateState(bookState.copy(title = it)) },
                )
                var expanded by remember { mutableStateOf(false) }
                val selectedCategory =
                    createBookViewModel.categories.value?.find { it.id == bookState.category }
                Spacer(modifier = Modifier.height(10.dp))
                Box {
                    TextDropDown(selectedCategory?.name ?: "Выберите категорию", "Категория", "Выберите категорию") {
                        expanded = it
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        createBookViewModel.categories.value!!.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    bookState.category = category.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextEdit(
                    value = bookState.description,
                    label = "Описание",
                    placeholder = "Введите описание книги",
                    onValueChanged = { createBookViewModel.updateState(bookState.copy(description = it)) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextEdit(
                    value = bookState.publication,
                    label = "Издание",
                    placeholder = "Введите информацию об издании",
                    onValueChanged = { createBookViewModel.updateState(bookState.copy(publication = it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))

        Spacer(Modifier.height(10.dp))
        when (resultState) {
            is ResultCondition.Error -> {
                ButtonNavigate("Добавить книгу") { createBookViewModel.CreateBook() }
                Text((resultState as ResultCondition.Error).message)
            }
            is ResultCondition.Init -> {
                ButtonNavigate("Добавить книгу") { createBookViewModel.CreateBook() }
            }
            ResultCondition.Loading -> {
                CircularProgressIndicator()
            }
            is ResultCondition.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.Home)
                    {
                        popUpTo(Routes.CreateBook) {
                            inclusive = true
                        }
                    }
                }
            }
        }
                Spacer(modifier = Modifier.height(16.dp))
                ButtonNavigate(
                    label = "Вернуться назад",
                    onClick = {
                        navController.navigate(Routes.Home)
                        {
                            popUpTo(Routes.CreateBook) {
                                inclusive = true
                            }
                        }
                    }
                )
    }
}