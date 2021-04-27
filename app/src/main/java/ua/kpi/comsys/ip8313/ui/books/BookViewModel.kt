package ua.kpi.comsys.ip8313.ui.books

import androidx.lifecycle.ViewModel

class BookViewModel: ViewModel() {
    val bookList: MutableList<Book> = mutableListOf()
    var currentBook: Book? = null
}