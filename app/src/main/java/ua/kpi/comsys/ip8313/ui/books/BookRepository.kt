package ua.kpi.comsys.ip8313.ui.books

class BookRepository(private val remoteDataSource: BookDataSource) {
    suspend fun getSearchedBooks(searchQuery: String): List<Book> {
        return remoteDataSource.getSearchedBooks(searchQuery)
    }
    suspend fun getBookData(bookId: String): Book {
        return remoteDataSource.getBookData(bookId)
    }
}