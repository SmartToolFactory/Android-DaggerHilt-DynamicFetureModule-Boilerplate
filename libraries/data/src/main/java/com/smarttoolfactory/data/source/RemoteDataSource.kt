package com.smarttoolfactory.data.source

import com.smarttoolfactory.data.api.SampleApi
import com.smarttoolfactory.data.model.remote.SampleData
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getSampleDataList(): List<SampleData>
}

class RemoteDataSourceImpl @Inject constructor(private val api: SampleApi) : RemoteDataSource {
    override suspend fun getSampleDataList(): List<SampleData> {
        TODO("Not yet implemented")
    }
}
