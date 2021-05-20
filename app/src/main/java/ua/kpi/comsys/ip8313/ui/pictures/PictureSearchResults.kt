package ua.kpi.comsys.ip8313.ui.pictures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Picture (
    @PrimaryKey val id: String,
    val webformatURL: String
)

data class PictureSearchResults (
    val hits: List<Picture>
)