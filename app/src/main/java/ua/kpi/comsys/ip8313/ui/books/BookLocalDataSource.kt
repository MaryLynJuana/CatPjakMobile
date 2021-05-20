package ua.kpi.comsys.ip8313.ui.books

class BookLocalDataSource(private val db: BookDatabase): BookDataSource {
    override suspend fun getSearchedBooks(searchQuery: String): List<Book> {
        return db.bookDao().getSearchedBooks(searchQuery)
    }
    override suspend fun getBookData(bookId: String): Book {
        return db.bookDao().getBookData(bookId)
    }
    override suspend fun saveBooks(bookList: List<Book>) {
        db.bookDao().saveBooks(bookList)
    }

    override suspend fun saveBookData(book: Book) {
        db.bookDao().saveBookData(book)
    }
}