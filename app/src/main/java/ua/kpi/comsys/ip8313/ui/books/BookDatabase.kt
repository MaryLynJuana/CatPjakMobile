package ua.kpi.comsys.ip8313.ui.books

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var instance: BookDatabase? = null
        private fun initDatabase(context: Context) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java,
                "book_database"
            ).build()
        }
        fun getDatabase(context: Context): BookDatabase {
            return instance ?: synchronized(this) {
                initDatabase(context)
                instance!!
            }
        }
    }
}