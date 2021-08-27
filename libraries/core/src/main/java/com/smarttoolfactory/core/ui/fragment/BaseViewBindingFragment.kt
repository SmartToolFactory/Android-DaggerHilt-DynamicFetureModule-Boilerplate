
package com.smarttoolfactory.core.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * BaseFragment to avoid writing data-binding code over again for each fragment.
 *
 * Generic approach forces Fragments to have specified number of ViewModels if added as generic parameter
 *
 * LifeCycle of Fragments
 *
 * * onAttach()
 * * onCreate()
 * * onCreateView() -> View is created or Fragment returned from back stack
 * * onViewCreated()
 * * onStart()
 * * onResume()
 * * onPause()
 * * onStop()
 * * onDestroyView() fragment sent to back stack / Back navigation -> onCreateView() is called
 * * onDestroy()
 * * onDetach()
 */
abstract class BaseViewBindingFragment : Fragment() {

    /**
     * Generic nullable [ViewBinding] that is set to null in [BaseFragment.onDestroyView]
     */
    internal var binding: ViewBinding? = null

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
