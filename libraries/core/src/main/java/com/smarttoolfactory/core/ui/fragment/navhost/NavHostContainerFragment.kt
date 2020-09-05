package com.smarttoolfactory.core.ui.fragment.navhost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.fragment.NavHostFragment
import com.smarttoolfactory.core.error.NavigationException
import com.smarttoolfactory.core.util.Event
import com.smarttoolfactory.core.viewmodel.NavControllerViewModel
import kotlin.reflect.KProperty

/**
 *
 * Container fragment for a [NavHostFragment] with a specified layout resource and
 * id of of [NavHostFragment] in layout belong to this fragment.
 *
 * Fragment created via layout resource that belong to a layout that contains a [NavHostFragment]
 *
 * Requires a [FragmentFactory] to be able to create this fragment which does not posses
 * an empty constructor.
 *
 */
class NavHostContainerFragment() : Fragment() {

    /**
     * Layout of fragment that contains [NavHostFragment] or DynamicNavHostFragment
     */
    private var layoutRes: Int = -1

    /**
     * Id of [NavHostFragment] or [DynamicNavHostFragment]
     */
    private var navHostFragmentId: Int = -1

    /**
     * Secondary Constructor to use with  [NavHostFragmentFactory]
     * to create fragment with parameters in constructor
     */
    constructor(@LayoutRes layoutRes: Int, @IdRes navHostFragmentId: Int) : this() {
        this.layoutRes = layoutRes
        this.navHostFragmentId = navHostFragmentId
    }

    private val navControllerViewModel by activityViewModels<NavControllerViewModel>()

    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (arguments?.get(KEY_RES_ID) as? Int)?.let {
            layoutRes = it
        }

        (arguments?.get(KEY_NAV_HOST_ID) as? Int)?.let {
            navHostFragmentId = it
        }

        val view = inflater.inflate(layoutRes, container, false)

        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(navHostFragmentId) as? NavHostFragment

        if (nestedNavHostFragment?.navController == null)
            throw NavigationException(
                "This fragment should have " +
                    "a NavHostFragment with NavController"
            )

        navController = nestedNavHostFragment.navController

        return view
    }

    override fun onResume() {
        super.onResume()
        // Set this navController as ViewModel's navController
        navControllerViewModel.currentNavController.value = Event(navController)
    }

    override fun onDestroyView() {
        navController = null
        navControllerViewModel.currentNavController.value = Event(null)
        super.onDestroyView()
    }

    companion object {
        const val KEY_RES_ID = "key-res-id"
        const val KEY_NAV_HOST_ID = "key-nav-host-id"
        const val DEFAULT_LAYOUT_ID = "default"

        @JvmStatic
        fun createNavHostContainerFragment(
            @LayoutRes layoutRes: Int,
            @IdRes navHostFragmentId: Int,
            fragmentTag: String? = null
        ): NavHostContainerFragment {

            return NavHostContainerFragment().apply {

                arguments = Bundle().apply {
                    putInt(KEY_RES_ID, layoutRes)
                    putInt(KEY_NAV_HOST_ID, navHostFragmentId)
                }
            }
        }
    }
}

class FieldProperty<R, T : Any>(
    val initializer: (R) -> T = { throw IllegalStateException("Not initialized.") }
) {
    private val map = HashMap<R, T>()

    operator fun getValue(thisRef: R, property: KProperty<*>): T =
        map[thisRef] ?: setValue(thisRef, property, initializer(thisRef))

    operator fun setValue(thisRef: R, property: KProperty<*>, value: T): T {
        map[thisRef] = value
        return value
    }
}
