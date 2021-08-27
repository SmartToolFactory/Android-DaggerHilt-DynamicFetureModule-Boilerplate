package com.smarttoolfactory.feature.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.smarttoolfactory.core.di.CoreModuleDependencies
import com.smarttoolfactory.feature.FeatureFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [CoreModuleDependencies::class],
    modules = [FeatureModule::class]
)
interface FeatureComponent {

    fun inject(fragment: FeatureFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreModuleDependencies: CoreModuleDependencies,
            @BindsInstance fragment: Fragment,
            @BindsInstance application: Application
        ): FeatureComponent
    }
}
