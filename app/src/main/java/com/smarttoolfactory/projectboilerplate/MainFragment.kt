package com.smarttoolfactory.projectboilerplate

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.smarttoolfactory.core.ui.fragment.DynamicNavigationFragment
import com.smarttoolfactory.projectboilerplate.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : DynamicNavigationFragment<FragmentMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGallery = view.findViewById<Button>(R.id.btnGallery)

        btnGallery.setOnClickListener {
            navigateWithInstallMonitor(findNavController(), R.id.nav_graph_gallery)
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_main
}
