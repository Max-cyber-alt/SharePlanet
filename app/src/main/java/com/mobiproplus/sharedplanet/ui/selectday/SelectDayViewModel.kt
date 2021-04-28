package com.mobiproplus.sharedplanet.ui.selectday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiproplus.sharedplanet.data.DataRepository
import com.mobiproplus.sharedplanet.data.model.NasaDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDayViewModel @Inject constructor(
    repository: DataRepository
) : ViewModel() {

    val dates: LiveData<List<NasaDate>> = repository.dates

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    private val _navigateToSelectTime = MutableLiveData<String?>()
    val navigateToSelectTime
        get() = _navigateToSelectTime

    init {
        launchDatesLoad { repository.fetchDates() }
    }

    private fun launchDatesLoad(fetchDates: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                fetchDates()
            } catch (error: Throwable) {
                _toastMessage.value = error.message
            }
        }
    }

    fun onDateClicked(date: String) {
        _navigateToSelectTime.value = date
    }

    fun onSelectedTimeNavigated() {
        _navigateToSelectTime.value = null
    }

    fun onToastShown() {
        _toastMessage.value = null
    }
}