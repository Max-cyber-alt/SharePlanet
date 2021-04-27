package com.mobiproplus.sharedplanet.di

import com.mobiproplus.sharedplanet.data.DataRepository
import com.mobiproplus.sharedplanet.data.local.LocalDataSource
import com.mobiproplus.sharedplanet.data.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource,
        networkDataSource: NetworkDataSource
    ): DataRepository {
        return DataRepository(localDataSource, networkDataSource)
    }
}