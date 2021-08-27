package com.smarttoolfactory.data.mapper

import com.smarttoolfactory.data.model.IEntity
import com.smarttoolfactory.data.model.local.SampleDataEntity
import com.smarttoolfactory.data.model.remote.SampleData
import javax.inject.Inject

/**
 * Mapper for transforming objects between REST and database or REST/db and domain
 * as [IEntity]  which are Non-nullable to Non-nullable
 */
interface Mapper<I, O> {
    fun map(input: I): O
}

/**
 * Mapper for transforming objects between REST and database or REST/db and domain
 * as [List] of [IEntity] which are Non-nullable to Non-nullable
 */
interface ListMapper<I, O> : Mapper<List<I>, List<O>>

class SampleDataMapper @Inject constructor() : Mapper<SampleData, SampleDataEntity> {
    override fun map(input: SampleData): SampleDataEntity {
        return SampleDataEntity(input.id)
    }
}
