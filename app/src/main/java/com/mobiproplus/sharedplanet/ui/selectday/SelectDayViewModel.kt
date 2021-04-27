package com.mobiproplus.sharedplanet.ui.selectday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiproplus.sharedplanet.data.DataRepository
import com.mobiproplus.sharedplanet.data.model.NasaDate
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectDayViewModel @Inject constructor(
    repository: DataRepository
) : ViewModel() {

    private val _dataInfo = MutableLiveData<List<NasaDate>>()
    val dataInfo: LiveData<List<NasaDate>>
        get() = _dataInfo

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    init {
        launchDatesLoad { repository.getDates() }
    }

    private fun launchDatesLoad(dates: suspend () -> List<NasaDate>) {
        viewModelScope.launch {
            try {
                _dataInfo.value = dates.invoke()
            } catch (error: Throwable) {
                _toastMessage.value = error.message
            }
        }
    }

    fun onToastShown() {
        _toastMessage.value = null
    }
}