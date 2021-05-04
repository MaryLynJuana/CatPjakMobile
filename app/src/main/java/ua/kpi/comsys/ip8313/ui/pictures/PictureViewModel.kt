package ua.kpi.comsys.ip8313.ui.pictures

import android.net.Uri
import androidx.lifecycle.ViewModel

class PictureViewModel: ViewModel() {
    val pictureList: MutableList<Uri> = mutableListOf()
}