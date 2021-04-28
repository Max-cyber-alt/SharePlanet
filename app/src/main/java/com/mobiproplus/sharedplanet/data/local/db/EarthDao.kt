package com.mobiproplus.sharedplanet.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobiproplus.sharedplanet.data.model.NasaDate
import com.mobiproplus.sharedplanet.data.model.NasaPhoto

@Dao
interface EarthDao {

    @Query("SELECT * FROM dates ORDER BY date")
    fun getDates(): LiveData<List<NasaDate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDates(dates: List<NasaDate>)

    @Query("SELECT * FROM photos ORDER BY identifier")
    fun getPhotos(): LiveData<List<NasaPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<NasaPhoto>)

}