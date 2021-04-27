package com.mobiproplus.sharedplanet.data.local

import com.mobiproplus.sharedplanet.data.local.db.EarthDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val earthDao: EarthDao
) {
    fun getSomeLocalData() = earthDao.getDates()
}