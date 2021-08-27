package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.model.remote.SampleData
import com.smarttoolfactory.data.source.LocalDataSource
import com.smarttoolfactory.data.source.RemoteDataSource
import javax.inject.Inject

interface Repository {

    suspend fun geSampleDataFromRemote(): List<SampleData>
}

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override suspend fun geSampleDataFromRemote(): List<SampleData> {
        TODO("Not yet implemented")
    }
}
