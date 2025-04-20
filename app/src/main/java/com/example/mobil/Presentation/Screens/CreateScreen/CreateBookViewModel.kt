package com.example.mobil.Presentation.Screens.SignUp

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.Domain.Constant.supabase
import com.example.mobil.Domain.Models.Book
import com.example.mobil.Domain.Models.Category
import com.example.mobil.Domain.Сondition.BookCondition
import com.example.mobil.Domain.Сondition.ResultCondition
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

class CreateBookViewModel: ViewModel() {


    private val _uiState = mutableStateOf(BookCondition())
    val uiState: BookCondition get() = _uiState.value

    private val _resultState = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultState: StateFlow<ResultCondition> = _resultState.asStateFlow()

    lateinit var book: Book

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun updateState(newState: BookCondition) {
        _state.value = newState
    }

    init {
        loadCategories()
    }

    private val _state = mutableStateOf(BookCondition())
    val state: BookCondition get() = _state.value

    fun CreateBook() {
        _resultState.value = ResultCondition.Loading
        viewModelScope.launch {
            try {
                val newBook = Book(
                    id = UUID.randomUUID().toString(),
                    title = _state.value.title,
                    categoryId = _state.value.category,
                    description = _state.value.description,
                    publication = _state.value.publication
                )
                supabase.postgrest.from("Book").insert(newBook)
                _resultState.value = ResultCondition.Success("Success")
            } catch (e: AuthRestException) {
                Log.e("CreateBook", "Error update data", e)
                _resultState.value = ResultCondition.Error(e.message.toString())
            }
        }
    }
    suspend fun getUrlImage(bookName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val url = supabase.storage.from("Book").publicUrl("${bookName}.png")
                Log.d("buck", url)
                url
            } catch (ex: AuthRestException) {
                Log.e("Error", "Failed to get URL: ${ex.message}")
                ""
            }
        }
    }
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value =
                    supabase.postgrest.from("Category").select().decodeList<Category>()
                Log.d("loadCategories", "Success")
                Log.d("loadCategories", _categories.toString())

            } catch (_ex: AuthRestException) {
                Log.d("loadCategories", _ex.message.toString())
                Log.d("loadCategories", _ex.errorCode.toString())
                Log.d("loadCategories", _ex.errorDescription)
            }
        }
    }
}