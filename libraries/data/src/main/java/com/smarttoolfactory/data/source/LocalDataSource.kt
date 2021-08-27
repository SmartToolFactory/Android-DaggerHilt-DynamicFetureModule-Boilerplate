package com.smarttoolfactory.data.source

import com.smarttoolfactory.data.db.dao.SampleDao
import com.smarttoolfactory.data.model.local.SampleDataEntity
import javax.inject.Inject

interface LocalDataSource {
    suspend fun getSampleEntities(): List<SampleDataEntity>
}

class LocalDataSourceImpl @Inject constructor(private val sampleDao: SampleDao) : LocalDataSource {

    override suspend fun getSampleEntities(): List<SampleDataEntity> {
        TODO("Not yet implemented")
    }
}
