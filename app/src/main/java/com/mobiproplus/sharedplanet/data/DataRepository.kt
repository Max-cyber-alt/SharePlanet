package com.mobiproplus.sharedplanet.data

import com.mobiproplus.sharedplanet.data.local.LocalDataSource
import com.mobiproplus.sharedplanet.data.network.NetworkDataSource
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getDates() = networkDataSource.getDates()

    suspend fun getPhotos(date: String) = networkDataSource.getPhotos(date)

}