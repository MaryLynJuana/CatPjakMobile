package ua.kpi.comsys.ip8313.ui.books

import java.lang.Exception

class BookRepository(
    private val remoteDataSource: BookDataSource,
    private val localDataSource: BookLocalDataSource
) {
    suspend fun getSearchedBooks(searchQuery: String): List<Book>? {
        return try {
            val loadedList = remoteDataSource.getSearchedBooks(searchQuery)
            localDataSource.saveBooks(loadedList)
            loadedList
        } catch (e: Exception) {
            val cachedList = localDataSource.getSearchedBooks(searchQuery)
                if (cachedList.isEmpty()) null else cachedList
        }
    }
    suspend fun getBookData(bookId: String): Book? {
        return try {
            val loadedBook = remoteDataSource.getBookData(bookId)
            localDataSource.saveBookData(loadedBook)
            loadedBook
        } catch (e: Exception) {
            localDataSource.getBookData(bookId)
        }
    }
}