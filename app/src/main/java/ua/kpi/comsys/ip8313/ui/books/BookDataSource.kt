package ua.kpi.comsys.ip8313.ui.books

interface BookDataSource {
    suspend fun getSearchedBooks(searchQuery: String): List<Book>
    suspend fun getBookData(bookId: String): Book
    suspend fun saveBooks(bookList: List<Book>)
    suspend fun saveBookData(book: Book)
}