package com.app.user_mangement.ui.screen.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.data.model.BookModel
import com.app.user_mangement.data.model.UiState
import com.app.user_mangement.data.respository.book_repos.BookRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookViewModel @Inject constructor(
    private  val bookRepository: BookRepostory
): ViewModel(){
    // Function to fetch books from the repository
    private val _books = MutableStateFlow<UiState<List<BookModel>>>(UiState.Loading)
    val bookListing : StateFlow<UiState<List<BookModel>>> = _books.asStateFlow()

    init {
        viewModelScope.launch {
            getBookListing()
        }
    }
  private  fun getBookListing() {
        viewModelScope.launch {
        _books.value = UiState.Loading
            val result = bookRepository.getBooksListing()
            if (result.isNotEmpty()) {
                _books.value = UiState.Success(result)
            } else {
                _books.value = UiState.Error("No books found")
            }
        }
     }
    fun updateBook(book: BookModel) {
        viewModelScope.launch {
            try {
                bookRepository.updateBook(id = book.id, book = book)
                getBookListing() // Refresh the book list after updating
            } catch (e: Exception) {
                _books.value = UiState.Error("Failed to update book: ${e.message}")
            }
        }
    }
    fun deleteBook(id: String) {
        viewModelScope.launch {
            try {
                bookRepository.deleteBook(id)
                getBookListing() // Refresh the book list after deletion
            } catch (e: Exception) {
                _books.value = UiState.Error("Failed to delete book: ${e.message}")
            }

        }
    }

    }