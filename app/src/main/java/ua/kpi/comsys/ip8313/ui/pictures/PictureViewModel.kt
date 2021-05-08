package ua.kpi.comsys.ip8313.ui.pictures

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PictureViewModel: ViewModel() {
    val pictureList = MutableLiveData<List<PictureData>>()
    private val repository = PictureRepository(PictureRemoteDataSource(getPicturesApi()))

    fun loadPictures(query: String) {
        viewModelScope.launch {
            val res = repository.getPictures(query)
            pictureList.postValue(res)
        }
    }
}