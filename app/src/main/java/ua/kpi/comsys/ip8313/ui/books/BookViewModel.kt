package ua.kpi.comsys.ip8313.ui.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookViewModel: ViewModel() {
    var searchQuery = ""
    val bookList = MutableLiveData<List<Book>>()
    var currentBook = MutableLiveData<Book>()
    private val repository = BookRepository(BookRemoteDataSource(getBookApi()))
    fun loadSearchedBooks(searchQuery: String) {
        viewModelScope.launch {
            val res = repository.getSearchedBooks(searchQuery)
            bookList.postValue(res)
        }
    }
    fun loadBookData(bookId: String) {
        viewModelScope.launch {
            val res = repository.getBookData(bookId)
            currentBook.postValue(res)
        }
    }
}