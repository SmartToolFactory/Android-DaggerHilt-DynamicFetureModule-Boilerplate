package com.smarttoolfactory.gallery.di

import android.app.Application
import android.content.Context
import com.smarttoolfactory.gallery.model.GalleryDependency
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module(includes = [GalleryBindModule::class])
class GalleryModule {

    @Provides
    fun provideGalleryObject(context: Context) = GalleryDependency(context)
}

@InstallIn(FragmentComponent::class)
@Module
abstract class GalleryBindModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}