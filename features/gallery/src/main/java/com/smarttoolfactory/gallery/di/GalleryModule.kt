package com.smarttoolfactory.gallery.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.gallery.model.GalleryDependency
import com.smarttoolfactory.gallery.viewmodel.FeatureViewModel
import com.smarttoolfactory.gallery.viewmodel.FeatureViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(FragmentComponent::class)
@Module(includes = [GalleryBindModule::class])
class GalleryModule {

    @Provides
    fun provideGalleryObject(context: Context) = GalleryDependency(context)

    @Provides
    fun provideFeatureViewModel(fragment: Fragment, factory: FeatureViewModelFactory) =
        ViewModelProvider(fragment, factory).get(FeatureViewModel::class.java)

    @Provides
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}

@InstallIn(FragmentComponent::class)
@Module
abstract class GalleryBindModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}
