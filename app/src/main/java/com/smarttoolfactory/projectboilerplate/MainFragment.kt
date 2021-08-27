package com.smarttoolfactory.projectboilerplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.smarttoolfactory.core.CoreDependency
import com.smarttoolfactory.core.di.CoreModuleDependencies
import com.smarttoolfactory.core.ui.fragment.DynamicNavigationFragment
import com.smarttoolfactory.projectboilerplate.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : DynamicNavigationFragment() {

    private var _binding: FragmentMainBinding? = null

    /**
     * Injected from [CoreModule] using [CoreModuleDependencies] in core module with @Singleton scope
     */
    @Inject
    lateinit var coreDependency: CoreDependency

    private val mainViewModel: MainViewModel by activityViewModels<MainViewModel>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDependencies.text =
            "CoreModule @Singleton coreDependency: ${coreDependency.hashCode()}\n" +
            "mainViewModel: ${mainViewModel.hashCode()}"

        binding.btnFeature.setOnClickListener {
            findNavController().navigate(R.id.nav_graph_feature)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
