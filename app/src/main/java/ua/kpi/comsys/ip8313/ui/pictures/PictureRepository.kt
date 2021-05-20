package ua.kpi.comsys.ip8313.ui.pictures

class PictureRepository(
    private val remoteDataSource: PictureRemoteDataSource,
    private val localDataSource: PictureLocalDataSource
) {
    suspend fun getPictures(query: String): List<Picture> {
        return try {
            val loadedList = remoteDataSource.getPictures(query)
            localDataSource.savePictures(loadedList)
            loadedList
        } catch (e: Exception) {
            localDataSource.getPictures(query)
        }
    }
}