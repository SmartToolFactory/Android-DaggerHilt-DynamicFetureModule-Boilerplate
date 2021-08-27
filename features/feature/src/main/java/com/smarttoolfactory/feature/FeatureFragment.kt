package com.smarttoolfactory.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.smarttoolfactory.core.CoreDependency
import com.smarttoolfactory.core.di.CoreModuleDependencies
import com.smarttoolfactory.feature.databinding.FragmentFeatureBinding
import com.smarttoolfactory.feature.di.DaggerFeatureComponent
import com.smarttoolfactory.feature.model.FeatureDependency
import com.smarttoolfactory.feature.viewmodel.FeatureViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FeatureFragment : Fragment() {

    /**
     * Injected from [CoreModule] using [CoreModuleDependencies] in core module with @Singleton scope
     */
    @Inject
    lateinit var coreDependency: CoreDependency

    /**
     * Injected from [FeatureModule] with @FeatureScope
     */
    @Inject
    lateinit var featureDependency: FeatureDependency

    @Inject
    lateinit var featureViewModel: FeatureViewModel

    private var _binding: FragmentFeatureBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeatureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvDependencies).text =
            "CoreModule @Singleton coreDependency: ${coreDependency.hashCode()}\n" +
            "Feature module no scope dependency: ${featureDependency.hashCode()}"
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

        DaggerFeatureComponent.factory().create(
            coreModuleDependencies = coreModuleDependencies,
            application = requireActivity().application,
            fragment = this
        )
            .inject(this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
