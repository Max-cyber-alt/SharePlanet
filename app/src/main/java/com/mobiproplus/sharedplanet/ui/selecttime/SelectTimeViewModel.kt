package com.mobiproplus.sharedplanet.ui.selecttime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiproplus.sharedplanet.data.DataRepository
import com.mobiproplus.sharedplanet.data.model.NasaPhoto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SelectTimeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    private val TAG = "SelectTimeViewModel"
    private val disposable: CompositeDisposable = CompositeDisposable()

    private val _nasaPhotos = MutableLiveData<List<NasaPhoto>>()
    val nasaPhotos: LiveData<List<NasaPhoto>>
        get() = _nasaPhotos

    fun getNasaPhotos(selectedDate: String) {
        disposable.add(repository.getPhotos(selectedDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> _nasaPhotos.value = result },
                { th -> Log.e(TAG, th.toString()) }
            )
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}