package com.example.mobil.Presentation.Screens.DetailScreen

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DetailsBookScreen(
    navController: NavHostController,
    id: String,
    viewModel: DetailsBookViewModel = viewModel { DetailsBookViewModel(id) }
) {
    val bookState = viewModel.state
    var imageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current

    val resultStateUpdate by viewModel.resultStateUpdate.collectAsState()
    val resultStateDelete by viewModel.resultStateDelete.collectAsState()
    val resultStateUpload by viewModel.resultStateUpload.collectAsState()


    when (resultStateUpload) {
        is ResultCondition.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = (resultStateUpload as ResultCondition.Error).message)
            }
        }

        ResultCondition.Init -> {}
        ResultCondition.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultCondition.Success -> {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Редактирование книги",
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
                            imageUrl = viewModel.getUrlImage(bookState.id)
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

                        Spacer(modifier = Modifier.width(20.dp))

                        TextEdit(
                            value = bookState.title,
                            label = "Название",
                            placeholder = "Введите название книги",
                            onValueChanged = { viewModel.updateState(bookState.copy(title = it)) },
                        )
                        var expanded by remember { mutableStateOf(false) }
                        val selectedCategory =
                            viewModel.categories.value?.find { it.id == bookState.category }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box {
                            TextDropDown(selectedCategory?.name ?: "Выберите категорию", "Категория", "Выберите категорию") {
                                expanded = it
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                viewModel.categories.value!!.forEach { category ->
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
                            onValueChanged = { viewModel.updateState(bookState.copy(description = it)) }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TextEdit(
                            value = bookState.publication,
                            label = "Издание",
                            placeholder = "Введите информацию об издании",
                            onValueChanged = { viewModel.updateState(bookState.copy(publication = it)) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        when (resultStateUpdate) {
                            is ResultCondition.Error -> {
                                    Toast.makeText(
                                        context,
                                        (resultStateUpload as ResultCondition.Error).message,
                                        Toast.LENGTH_SHORT
                                    )
                                ButtonNavigate(
                                    label = "Сохранить изменения",
                                    onClick = {
                                        viewModel.updateBook()
                                    }
                                )
                            }

                            ResultCondition.Init -> {
                                ButtonNavigate(
                                    label = "Сохранить изменения",
                                    onClick = {
                                        viewModel.updateBook()
                                    }
                                )
                            }

                            ResultCondition.Loading -> {
                                CircularProgressIndicator()
                            }

                            is ResultCondition.Success -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultCondition.Success).message,
                                    Toast.LENGTH_SHORT
                                )
                                ButtonNavigate(
                                    label = "Сохранить изменения",
                                    onClick = {
                                        viewModel.updateBook()
                                    }
                                )
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        ButtonNavigate(
                            label = "Вернуться назад",
                            onClick = {
                                navController.navigate(Routes.Home)
                                {
                                    popUpTo(Routes.DetailsBook) {
                                        inclusive = true
                                    }
                                }
                            }
                        )

                        Spacer(Modifier.height(10.dp))

                        when (resultStateDelete) {
                            is ResultCondition.Error -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultCondition.Error).message,
                                    Toast.LENGTH_SHORT
                                )
                                ButtonNavigate(
                                    label = "Удалить книгу",
                                    onClick = {
                                        viewModel.deleteBook()
                                    }
                                )
                            }

                            ResultCondition.Init -> {
                                ButtonNavigate(
                                    label = "Удалить книгу",
                                    onClick = {
                                        viewModel.deleteBook()
                                    }
                                )
                            }

                            ResultCondition.Loading -> {
                                CircularProgressIndicator()
                            }

                            is ResultCondition.Success -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultCondition.Success).message,
                                    Toast.LENGTH_SHORT
                                )
                                navController.navigate(Routes.Home)
                                {
                                    popUpTo(Routes.DetailsBook) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                }
            }
        }
    }

}
