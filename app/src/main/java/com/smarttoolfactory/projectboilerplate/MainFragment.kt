package com.smarttoolfactory.projectboilerplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.smarttoolfactory.core.ui.base.DynamicInstallFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : DynamicInstallFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGallery = view.findViewById<Button>(R.id.btnGallery)

        btnGallery.setOnClickListener {
            navigateWithInstallMonitor(findNavController(), R.id.nav_graph_gallery)
        }
    }
}
