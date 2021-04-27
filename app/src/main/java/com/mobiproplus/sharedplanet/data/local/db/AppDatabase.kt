package com.mobiproplus.sharedplanet.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobiproplus.sharedplanet.data.model.NasaDate
import com.mobiproplus.sharedplanet.data.model.NasaPhoto

@Database(entities = [NasaDate::class, NasaPhoto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun earthDao(): EarthDao
}