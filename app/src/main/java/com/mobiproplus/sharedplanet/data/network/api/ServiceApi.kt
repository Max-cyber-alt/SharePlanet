package com.mobiproplus.sharedplanet.data.network.api

import com.mobiproplus.sharedplanet.data.model.NasaDate
import com.mobiproplus.sharedplanet.data.model.NasaPhoto
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    companion object {
        const val BASE_URL = "https://api.nasa.gov/EPIC/api/"
        const val API_KEY = "uNThvWrh6FILm45gVkesGPklMcyiGauwWRdfDGck"
    }

    @GET("natural/all")
    suspend fun getDatesWithPhoto() : List<NasaDate>

    @GET("natural/date/{date}")
    suspend fun getPhotosByDate(@Path("date") date: String) : List<NasaPhoto>
}