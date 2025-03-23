package com.example.mobil.Presentation.Screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Presentation.Screens.Details.Books
import com.example.mobil.Presentation.Screens.Details.CategoryUnit
import com.example.mobil.Presentation.Screens.Details.TextSearch
import kotlinx.coroutines.runBlocking


@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val textSearch = remember { mutableStateOf("") }
    val categories = homeViewModel.categories.observeAsState(emptyList())
    val books = homeViewModel.books.observeAsState(emptyList())
    val selectedCategory = remember { mutableIntStateOf(-1) }
    val resultCondition by homeViewModel.resultCondition.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextSearch(
            value = textSearch.value,
            onvaluechange = { newText ->
                textSearch.value = newText
                homeViewModel.filterList(newText, selectedCategory.intValue)
            }
        )
        when (resultCondition) {
            is ResultCondition.Error ->
                Text(text = (resultCondition as ResultCondition.Error).message)

            ResultCondition.Init -> TODO()
            ResultCondition.Loading -> {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResultCondition.Success -> {
                LazyRow {
                    items(categories.value.indices.toList()) { index ->
                        CategoryUnit(
                            category = categories.value[index].name,
                            isSelected = selectedCategory.intValue == categories.value[index].id,
                            onClick = {
                                selectedCategory.intValue = categories.value[index].id
                                homeViewModel.filterList(
                                    textSearch.value,
                                    selectedCategory.intValue
                                )
                            }
                        )
                    }
                }
                LazyColumn {
                    items(books.value) { it ->
                        Books(book = it) {
                            runBlocking {
                                homeViewModel.getUrlImage(it)
                            }
                        }
                    }
                }
            }
        }
    }
}