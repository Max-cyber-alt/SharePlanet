package com.mobiproplus.sharedplanet.data.local

import com.mobiproplus.sharedplanet.data.local.db.DatabaseFaker
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val databaseFaker: DatabaseFaker
) {
    fun getSomeLocalData() = databaseFaker.getSomeData()
}