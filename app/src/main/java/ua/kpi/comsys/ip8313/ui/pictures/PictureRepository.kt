package ua.kpi.comsys.ip8313.ui.pictures

class PictureRepository(private val remoteDataSource: PictureRemoteDataSource) {
    suspend fun getPictures(query: String): List<PictureData> {
        return remoteDataSource.getPictures(query)
    }
}