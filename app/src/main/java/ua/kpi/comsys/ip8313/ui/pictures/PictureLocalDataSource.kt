package ua.kpi.comsys.ip8313.ui.pictures

class PictureLocalDataSource(private val db: PictureDatabase): PictureDataSource {
    override suspend fun getPictures(q: String): List<Picture> {
        return db.pictureDao().getPictures()
    }

    override suspend fun savePictures(pictureList: List<Picture>) {
        db.pictureDao().savePictures(pictureList)
    }
}