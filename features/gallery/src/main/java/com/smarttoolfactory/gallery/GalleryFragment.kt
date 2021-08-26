package com.smarttoolfactory.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.smarttoolfactory.core.CoreDependency
import com.smarttoolfactory.core.di.CoreModuleDependencies
import com.smarttoolfactory.gallery.databinding.FragmentGalleryBinding
import com.smarttoolfactory.gallery.di.DaggerGalleryComponent
import com.smarttoolfactory.gallery.model.GalleryDependency
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class GalleryFragment : Fragment() {

    /**
     * Injected from [CoreModule] using [CoreModuleDependencies] in core module with @Singleton scope
     */
    @Inject
    lateinit var coreDependency: CoreDependency

    /**
     * Injected from [GalleryModule] with @FeatureScope
     */
    @Inject
    lateinit var galleryDependency: GalleryDependency

    private var _binding: FragmentGalleryBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvDependencies).text =
            "CoreModule @Singleton coreDependency: ${coreDependency.hashCode()}\n" +
            "GalleryModule no scope galleryDependency: ${galleryDependency.hashCode()}"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        initCoreDependentInjection()
        super.onCreate(savedInstanceState)
    }

    private fun initCoreDependentInjection() {

        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            CoreModuleDependencies::class.java
        )

        DaggerGalleryComponent.factory().create(
            coreModuleDependencies,
            requireActivity().application
        )
            .inject(this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
