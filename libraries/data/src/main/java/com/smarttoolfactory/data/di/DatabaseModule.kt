package com.smarttoolfactory.data.di

import android.app.Application
import androidx.room.Room
import com.smarttoolfactory.data.constant.DATABASE_NAME
import com.smarttoolfactory.data.db.SampleDatabase
import com.smarttoolfactory.data.db.dao.SampleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): SampleDatabase {
        return Room.databaseBuilder(
            application,
            SampleDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideSessionTokenDao(appDatabase: SampleDatabase): SampleDao =
        appDatabase.sampleDao()
}
