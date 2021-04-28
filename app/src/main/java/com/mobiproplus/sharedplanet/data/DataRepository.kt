package com.mobiproplus.sharedplanet.data

import com.mobiproplus.sharedplanet.data.local.LocalDataSource
import com.mobiproplus.sharedplanet.data.network.NetworkDataSource
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) {

    val dates = localDataSource.getLocalDates()

    val photos = localDataSource.getLocalPhotos()

    suspend fun fetchDates() {
        val dates = networkDataSource.getDates()
        localDataSource.saveLocalDates(dates)

    }

    suspend fun getPhotos(date: String) {
        val photos = networkDataSource.getPhotos(date)
        localDataSource.saveLocalPhotos(photos)
    }

}