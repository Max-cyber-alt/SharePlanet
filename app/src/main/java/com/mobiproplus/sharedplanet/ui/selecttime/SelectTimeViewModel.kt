package com.mobiproplus.sharedplanet.ui.selecttime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiproplus.sharedplanet.data.DataRepository
import com.mobiproplus.sharedplanet.data.model.NasaPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SelectTimeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private val _photos = MutableLiveData<List<NasaPhoto>>()
    val photos: LiveData<List<NasaPhoto>>
        get() = _photos

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    fun getNasaPhotos(selectedDate: String) {
        launchPhotosLoad { repository.getPhotos(selectedDate) }
    }

    private fun launchPhotosLoad(photos: suspend () -> List<NasaPhoto>): Job {
        return viewModelScope.launch {
            try {
                _photos.value = photos()
            } catch (error: Throwable) {
                Timber.e(error)
                _toastMessage.value = error.message
            }
        }
    }

    fun onToastShown() {
        _toastMessage.value = null
    }
}