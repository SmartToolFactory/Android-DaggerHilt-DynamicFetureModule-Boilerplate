package com.smarttoolfactory.core.di

import com.smarttoolfactory.core.CoreDependency
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class CoreModule {

    @Singleton
    fun provideCoreDependency() = CoreDependency()

}
