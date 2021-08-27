package com.smarttoolfactory.feature.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.feature.model.FeatureDependency
import com.smarttoolfactory.feature.viewmodel.FeatureViewModel
import com.smarttoolfactory.feature.viewmodel.FeatureViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(FragmentComponent::class)
@Module(includes = [FeatureBindModule::class])
class FeatureModule {

    @Provides
    fun provideFeatureObject(context: Context) = FeatureDependency(context)

    @Provides
    fun provideFeatureViewModel(fragment: Fragment, factory: FeatureViewModelFactory) =
        ViewModelProvider(fragment, factory).get(FeatureViewModel::class.java)

    @Provides
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}

@InstallIn(FragmentComponent::class)
@Module
abstract class FeatureBindModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}
