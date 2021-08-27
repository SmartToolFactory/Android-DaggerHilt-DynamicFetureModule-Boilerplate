package com.smarttoolfactory.core.di

import com.smarttoolfactory.core.CoreDependency
import com.smarttoolfactory.domain.usecase.UseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This component is required for adding dependencies to Dynamic Feature Modules by
 * adding [CoreModule] as dependent component
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreModuleDependencies {

    /*
       Provision methods to provide dependencies to components that depend on this component
     */
    fun useCase(): UseCase
    fun coreDependency(): CoreDependency
}
