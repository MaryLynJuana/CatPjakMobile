package ua.kpi.comsys.ip8313.ui.pictures

interface PictureDataSource {
    suspend fun getPictures(q: String): List<Picture>
    suspend fun savePictures(pictureList: List<Picture>)
}