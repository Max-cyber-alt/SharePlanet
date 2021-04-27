package com.mobiproplus.sharedplanet.di

import android.content.Context
import androidx.room.Room
import com.mobiproplus.sharedplanet.data.local.db.AppDatabase
import com.mobiproplus.sharedplanet.data.local.db.EarthDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {

    @Provides
    @Singleton
    fun provideEarthDao(appDatabase: AppDatabase): EarthDao {
        return appDatabase.earthDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "planet_db").build()
    }
}