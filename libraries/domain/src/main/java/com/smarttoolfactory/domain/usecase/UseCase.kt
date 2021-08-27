package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.model.remote.SampleData
import com.smarttoolfactory.data.repository.Repository
import com.smarttoolfactory.domain.dispatcher.UseCaseDispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UseCase @Inject constructor(
    private val repository: Repository,
    private val useCaseDispatchers: UseCaseDispatchers
) {

    fun getListFlow(): Flow<List<SampleData>> {
        return flow { emit(repository.geSampleDataFromRemote()) }
            .flowOn(useCaseDispatchers.defaultDispatcher)
    }
}
