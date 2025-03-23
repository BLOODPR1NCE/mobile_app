package com.example.mobil.Presentation.Screens.Home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.Domain.Constant.supabase
import com.example.mobil.Domain.Models.Book
import com.example.mobil.Domain.Models.Category
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

class HomeViewModel : ViewModel() {
    private val _resultCondition = MutableStateFlow<ResultCondition>(ResultCondition.Loading)
    val resultCondition: StateFlow<ResultCondition> = _resultCondition.asStateFlow()

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private var allBooks: List<Book> = listOf()

    init {
        loadBooks()
        loadCategories()
    }

    private fun loadBooks() {
        _resultCondition.value = ResultCondition.Loading
        viewModelScope.launch {
            try {
                allBooks = supabase.postgrest.from("books").select().decodeList<Book>()
                _books.value = allBooks
                Log.d("MainBooks", "Success")
                Log.d("MainBooks", allBooks.toString())

                _resultCondition.value = ResultCondition.Success("Success")
            } catch (_ex: AuthRestException) {
                Log.d("MainBooks", _ex.message.toString())
                Log.d("MainBooks", _ex.errorCode.toString())
                Log.d("MainBooks", _ex.errorDescription)

                _resultCondition.value = ResultCondition.Error(_ex.errorDescription)
            }
        }
    }

    suspend fun getUrlImage(bookName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val url = supabase.storage.from("Books").publicUrl("${bookName}.png")
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
                _categories.value = supabase.postgrest.from("categories").select().decodeList<Category>()
                Log.d("MainCategories", "Success")
                Log.d("MainCategories", _categories.toString())

            } catch (_ex: AuthRestException) {
                Log.d("Main", _ex.message.toString())
                Log.d("Main", _ex.errorCode.toString())
                Log.d("Main", _ex.errorDescription)
            }
        }
    }

    fun filterList(query: String?, categoryId: Int?) {
        val filteredBooks = allBooks.filter { book ->
            val matchesTitle = query.isNullOrEmpty() || book.title.contains(query, ignoreCase = true) || book.description.contains(query, ignoreCase = true)
            val matchesCategory = categoryId == -1 || book.categoryId == categoryId
            matchesTitle && matchesCategory
        }
        _books.value = filteredBooks
    }
}
