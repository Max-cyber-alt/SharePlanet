package com.mobiproplus.sharedplanet.data.network

import com.mobiproplus.sharedplanet.data.network.api.ServiceApi
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val serviceApi: ServiceApi
) {
    fun getDates() = serviceApi.getDatesWithPhoto()
    fun getPhotos(date: String) = serviceApi.getPhotosByDate(date)
}