package com.mobiproplus.sharedplanet.data.local

import com.mobiproplus.sharedplanet.data.local.db.EarthDao
import com.mobiproplus.sharedplanet.data.model.NasaDate
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val earthDao: EarthDao
) {
    fun getLocalDates() = earthDao.getDates()

    suspend fun saveLocalDates(dates: List<NasaDate>) = earthDao.insertDates(dates)
}