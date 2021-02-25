package kim.uno.simpleapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kim.uno.simpleapp.R
import kim.uno.simpleapp.databinding.SearchFragmentBinding
import kim.uno.simpleapp.ui.BaseFragment
import kim.uno.simpleapp.util.hideKeyboard
import kim.uno.simpleapp.util.observe
import kim.uno.simpleapp.util.setOnEditorAction
import kim.uno.simpleapp.util.toast

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    private val preDrawListener: ViewTreeObserver.OnPreDrawListener by lazy {
        ViewTreeObserver.OnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    override fun onCreateViewOnce(inflater: LayoutInflater): View {
        binding = SearchFragmentBinding.inflate(inflater)
        binding.viewModel = searchViewModel
        binding.refreshLayout.setOnRefreshListener { searchViewModel.refresh() }
        binding.recyclerView.viewTreeObserver.addOnPreDrawListener(preDrawListener)
        binding.recyclerView.adapter = SearchAdapter {
            if (it == binding.recyclerView.adapter!!.itemCount - 10)
                searchViewModel.loadMore()
        }
        binding.search.setOnEditorAction { it.hideKeyboard() }
        binding.search.requestFocus()

        observe(searchViewModel.progress) { binding.refreshLayout.isRefreshing = it }
        observe(searchViewModel.error) { if (it) toast(R.string.error_network) }

        installTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter?.notifyDataSetChanged()
        postponeEnterTransition()
    }

}