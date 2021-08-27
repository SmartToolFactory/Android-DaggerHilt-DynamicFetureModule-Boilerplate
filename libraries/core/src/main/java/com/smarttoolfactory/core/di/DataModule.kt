package com.smarttoolfactory.core.di

import com.smarttoolfactory.data.di.DatabaseModule
import com.smarttoolfactory.data.di.NetworkModule
import com.smarttoolfactory.data.mapper.SampleDataMapper
import com.smarttoolfactory.data.repository.Repository
import com.smarttoolfactory.data.repository.RepositoryImpl
import com.smarttoolfactory.data.source.LocalDataSource
import com.smarttoolfactory.data.source.LocalDataSourceImpl
import com.smarttoolfactory.data.source.RemoteDataSource
import com.smarttoolfactory.data.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DataProviderModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
/**
 * This dagger module binds concrete implementations to interface required by
 * **data** and **domain** structure modules.
 *
 * For instance
 * ```
 * class LoginRepositoryImpl @Inject constructor(
 *      private val remoteDataSource: RemoteDataSource,
 *      private val localDataSource: LocalDataSource,
 *      private val mapper: Mapper
 *  ) : Repository {
 *
 * ```
 *
 * requires interfaces instead of concrete implementations.
 */
interface DataModule {

    @Singleton
    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl):
        RemoteDataSource

    @Singleton
    @Binds
    fun bindLocalDataSource(localDataSource: LocalDataSourceImpl):
        LocalDataSource

    @Singleton
    @Binds
    fun bindLoginRepository(repository: RepositoryImpl):
        Repository
}

/**
 * This module is for injections with @Provides annotation
 */
@Module
@InstallIn(SingletonComponent::class)
object DataProviderModule {
    @Provides
    fun provideMapper() = SampleDataMapper()
}
