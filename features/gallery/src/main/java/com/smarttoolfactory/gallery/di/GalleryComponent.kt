package com.smarttoolfactory.gallery.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.smarttoolfactory.core.di.CoreModuleDependencies
import com.smarttoolfactory.gallery.GalleryFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [CoreModuleDependencies::class],
    modules = [GalleryModule::class]
)
interface GalleryComponent {

    fun inject(fragment: GalleryFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreModuleDependencies: CoreModuleDependencies,
            @BindsInstance fragment: Fragment,
            @BindsInstance application: Application
        ): GalleryComponent
    }
}
