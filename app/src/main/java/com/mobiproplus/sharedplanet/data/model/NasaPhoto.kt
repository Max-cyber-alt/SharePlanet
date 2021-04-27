package com.mobiproplus.sharedplanet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobiproplus.sharedplanet.data.network.api.ServiceApi

@Entity(tableName = "photos")
data class NasaPhoto(
    @PrimaryKey @ColumnInfo(name = "identifier") val identifier: String = "",
    @ColumnInfo(name = "caption") val caption: String = "",
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "date") var date: String = ""
) {

    fun getImageUrl(): String {
        //https://api.nasa.gov/EPIC/archive/enhanced/2016/12/04/png/epic_RBG_20161204003633.png?api_key=DEMO_KEY
        val sb = StringBuilder()
        sb.append("https://api.nasa.gov/EPIC/archive/natural/")
        val dateComponents: List<String> = date.split(" ")[0].split("-")
        sb.append(dateComponents[0]).append('/')
            .append(dateComponents[1]).append('/')
            .append(dateComponents[2]).append("/png/")
            .append(image).append(".png?api_key=").append(ServiceApi.API_KEY)
        return sb.toString()
    }
}