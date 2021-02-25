package kim.uno.simpleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.transition.Explode
import androidx.transition.TransitionInflater
import kim.uno.simpleapp.R

abstract class BaseFragment : Fragment() {

    private lateinit var itemView: View
    private val explode by lazy { Explode().apply { interpolator = DecelerateInterpolator() } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::itemView.isInitialized) {
            val view = onCreateViewOnce(inflater)
            if (view != null) itemView = view
            else return null!!
        }
        return itemView
    }

    fun installTransition() {
        enterTransition = explode
        exitTransition = explode
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_fragment)
    }

    open fun onCreateViewOnce(inflater: LayoutInflater): View? = null

}