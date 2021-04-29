package com.mobiproplus.sharedplanet.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mobiproplus.sharedplanet.data.model.NasaDate

@Dao
interface EarthDao {

    @Query("SELECT * FROM dates")
    fun getDates(): LiveData<List<NasaDate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDates(dates: List<NasaDate>)

}