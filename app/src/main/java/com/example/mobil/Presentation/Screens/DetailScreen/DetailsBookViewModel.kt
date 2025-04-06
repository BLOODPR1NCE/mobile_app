package com.example.mobil.Presentation.Screens.DetailScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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

class DetailsBookViewModel(id: String) : ViewModel() {

    var idBook = id

    private val _resultStateUpdate = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultStateUpdate: StateFlow<ResultCondition> = _resultStateUpdate.asStateFlow()

    private val _resultStateDelete = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultStateDelete: StateFlow<ResultCondition> = _resultStateDelete.asStateFlow()

    private val _resultStateUpload = MutableStateFlow<ResultCondition>(ResultCondition.Init)
    val resultStateUpload: StateFlow<ResultCondition> = _resultStateUpload.asStateFlow()

    lateinit var book: Book


    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun updateState(newState: BookCondition) {
        _state.value = newState
    }

    private val _state = mutableStateOf(BookCondition())
    val state: BookCondition get() = _state.value

    init {
        loadCategories()
        getBook()
    }

    fun getBook() {
        _resultStateUpload.value = ResultCondition.Loading
        viewModelScope.launch {
            try {
                book = supabase.postgrest.from("Book").select() {
                    filter {
                        eq("id", idBook)
                    }
                }.decodeSingle<Book>()

                _state.value = BookCondition(
                    id = book.id,
                    title = book.title,
                    category = book.categoryId,
                    description = book.description,
                    genre = book.genre
                )
                _resultStateUpload.value = ResultCondition.Success("Success")
            } catch (e: AuthRestException) {
                Log.e("getBook", "Error loading data", e)
                _resultStateUpload.value = ResultCondition.Error(e.message.toString())
            }
        }
    }

    fun updateBook() {
        _resultStateUpdate.value = ResultCondition.Loading
        viewModelScope.launch {
            try {
                supabase.postgrest.from("Book").update(
                    {
                        set("title", _state.value.title)
                        set("category_id", _state.value.category)
                        set("description", _state.value.description)
                        set("genre", _state.value.genre)
                    }
                ) {
                    filter {
                        eq("id", idBook)
                    }
                }
                _resultStateUpdate.value = ResultCondition.Success("Success")
            } catch (e: AuthRestException) {
                Log.e("updateBook", "Error update data", e)
                _resultStateUpdate.value = ResultCondition.Error(e.message.toString())
            }
        }
    }
    fun deleteBook()
    {
        _resultStateDelete.value = ResultCondition.Loading
        viewModelScope.launch {
            try {
                supabase.postgrest.from("Book").delete(
                ) {
                    filter {
                        eq("id", idBook)
                    }
                }
                _resultStateDelete.value = ResultCondition.Success("Delete")
            } catch (e: AuthRestException) {
                Log.e("deleteBook", "Error delete data", e)
                _resultStateDelete.value = ResultCondition.Error(e.message.toString())
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
}