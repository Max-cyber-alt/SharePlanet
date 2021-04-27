package com.mobiproplus.sharedplanet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dates")
data class NasaDate(@PrimaryKey @ColumnInfo(name = "date") val date: String = "")