package ua.kpi.comsys.ip8313.ui.pictures

data class PictureData (
    val id: String,
    val webformatURL: String
)

data class PictureSearchResults (
    val hits: List<PictureData>
)