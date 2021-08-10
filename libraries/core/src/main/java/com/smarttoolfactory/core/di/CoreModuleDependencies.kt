
package com.smarttoolfactory.core.di

import com.smarttoolfactory.core.CoreDependency
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This component is required for adding component to Dynamic Feature Module dependencies
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreModuleDependencies {

    /*
       Provision methods to provide dependencies to components that depend on this component
     */
    fun coreDependency(): CoreDependency
}
