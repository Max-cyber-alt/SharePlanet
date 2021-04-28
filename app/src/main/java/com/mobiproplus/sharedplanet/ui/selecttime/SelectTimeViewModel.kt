package com.mobiproplus.sharedplanet.ui.selecttime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiproplus.sharedplanet.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectTimeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val photos = repository.photos

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    private val _navigateToPhoto = MutableLiveData<String?>()
    val navigateToPhoto
        get() = _navigateToPhoto

    fun getNasaPhotos(selectedDate: String) {
        launchPhotosLoad { repository.getPhotos(selectedDate) }
    }

    private fun launchPhotosLoad(fetchPhotos: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                fetchPhotos()
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