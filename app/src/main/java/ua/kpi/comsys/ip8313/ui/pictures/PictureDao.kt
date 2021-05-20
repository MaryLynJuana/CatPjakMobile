package ua.kpi.comsys.ip8313.ui.pictures

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureDao {
    @Query("SELECT * FROM picture")
    suspend fun getPictures(): List<Picture>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePictures(pictureList: List<Picture>)
}