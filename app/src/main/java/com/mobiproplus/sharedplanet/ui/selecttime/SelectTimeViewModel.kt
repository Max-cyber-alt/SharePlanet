package com.mobiproplus.sharedplanet.ui.selecttime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiproplus.sharedplanet.data.DataRepository
import com.mobiproplus.sharedplanet.data.model.NasaPhoto
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectTimeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private val _nasaPhotos = MutableLiveData<List<NasaPhoto>>()
    val nasaPhotos: LiveData<List<NasaPhoto>>
        get() = _nasaPhotos

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    private val _navigateToPhoto = MutableLiveData<String?>()
    val navigateToPhoto
        get() = _navigateToPhoto

    fun getNasaPhotos(selectedDate: String) {
        launchPhotosLoad { repository.getPhotos(selectedDate) }
    }

    private fun launchPhotosLoad(photos: suspend () -> List<NasaPhoto>) {
        viewModelScope.launch {
            try {
                _nasaPhotos.value = photos.invoke()
            } catch (error: Throwable) {
                _toastMessage.value = error.message
            }
        }
    }

    fun onTimeClicked(imageUrl: String) {
        _navigateToPhoto.value = imageUrl
    }

    fun onPhotoNavigated() {
        _navigateToPhoto.value = null
    }

    fun onToastShown() {
        _toastMessage.value = null
    }
}