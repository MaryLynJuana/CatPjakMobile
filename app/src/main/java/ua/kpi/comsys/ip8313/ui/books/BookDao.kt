package ua.kpi.comsys.ip8313.ui.books

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM book WHERE instr(UPPER(title), UPPER(:q)) > 0")
    suspend fun getSearchedBooks(q: String): List<Book>
    @Query("SELECT * FROM book WHERE isbn13 = :id")
    suspend fun getBookData(id: String): Book
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBooks(bookList: List<Book>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookData(book: Book)
}