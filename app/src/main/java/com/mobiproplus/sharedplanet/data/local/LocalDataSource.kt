package com.mobiproplus.sharedplanet.data.local

import com.mobiproplus.sharedplanet.data.local.db.EarthDao
import com.mobiproplus.sharedplanet.data.model.NasaDate
import com.mobiproplus.sharedplanet.data.model.NasaPhoto
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val earthDao: EarthDao
) {
    fun getLocalDates() = earthDao.getDates()

    fun getLocalPhotos() = earthDao.getPhotos()

    suspend fun saveLocalDates(dates: List<NasaDate>) = earthDao.insertDates(dates)

    suspend fun saveLocalPhotos(photos: List<NasaPhoto>) = earthDao.insertPhotos(photos)
}