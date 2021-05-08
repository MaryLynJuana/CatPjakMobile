package ua.kpi.comsys.ip8313.ui.pictures

class PictureRemoteDataSource(private val api: PicturesApi): PictureDataSource {
    override suspend fun getPictures(query: String): List<PictureData> {
        return api.getPictures(q = query).hits
    }
}