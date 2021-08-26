// package com.smarttoolfactory.core.ui.fragment
//
// import android.graphics.Point
// import android.os.Bundle
// import android.view.LayoutInflater
// import android.view.View
// import android.view.ViewGroup
// import androidx.viewbinding.ViewBinding
// import androidx.annotation.LayoutRes
// import androidx.fragment.app.Fragment
//
// /**
// * BaseFragment to avoid writing data-binding code over again for each fragment.
// *
// * Generic approach forces Fragments to have specified number of ViewModels if added as generic parameter
// *
// * LifeCycle of Fragments
// *
// * * onAttach()
// * * onCreate()
// * * onCreateView() -> View is created or Fragment returned from back stack
// * * onViewCreated()
// * * onStart()
// * * onResume()
// * * onPause()
// * * onStop()
// * * onDestroyView() fragment sent to back stack / Back navigation -> onCreateView() is called
// * * onDestroy()
// * * onDetach()
// */
// abstract class BaseViewBindingFragment : Fragment() {
//
//    /**
//     * Generic nullable [ViewBinding] that is set to null in [BaseFragment.onDestroyView]
//     */
//    internal var _binding: ViewBinding? = null
//
//
//    /**
//     * Point that contains width and height of the fragment.
//     *
//     */
//    private val dimensions = Point()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        // Each fragment can have it's separate toolbar menu
//        setHasOptionsMenu(true)
//
//        // Get width and height of the fragment
//        rootView.post {
//            dimensions.x = rootView.width
//            dimensions.y = rootView.height
//        }
//
//        bindViews()
//
//        return rootView
//    }
//
//    override fun onDestroyView() {
//        _binding = null
//        super.onDestroyView()
//    }
//
//    /**
//     * Called from [Fragment.onCreateView] to implement bound ui items and set properties
//     */
//    open fun bindViews() {
//    }
// }
