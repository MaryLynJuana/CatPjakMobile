package ua.kpi.comsys.ip8313.ui.pictures

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Picture::class], version = 1)
abstract class PictureDatabase: RoomDatabase() {
    abstract fun pictureDao(): PictureDao

    companion object {
        private var instance: PictureDatabase? = null
        private fun initDatabase(context: Context) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                PictureDatabase::class.java,
                "picture_database"
            ).build()
        }
        fun getDatabase(context: Context): PictureDatabase {
            return instance ?: synchronized(this) {
                initDatabase(context)
                instance!!
            }
        }
    }
}