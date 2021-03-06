package kim.uno.simpleapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kim.uno.simpleapp.R
import kim.uno.simpleapp.databinding.SearchFragmentBinding
import kim.uno.simpleapp.extension.hideKeyboard
import kim.uno.simpleapp.extension.observe
import kim.uno.simpleapp.extension.setOnEditorAction
import kim.uno.simpleapp.extension.toast
import kim.uno.simpleapp.ui.BaseFragment

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
        binding.lifecycleOwner = this
        binding.refreshLayout.setOnRefreshListener { searchViewModel.refresh() }
        binding.recyclerView.apply {
            viewTreeObserver.addOnPreDrawListener(preDrawListener)
            adapter = SearchAdapter(binder = {
                if (it == binding.recyclerView.adapter!!.itemCount - 10)
                    searchViewModel.loadMore()
            }, favorite = searchViewModel::isFavorite,
            documentDetail = searchViewModel::documentDetail)
        }

        binding.search.apply {
            setOnEditorAction { it.hideKeyboard() }
            requestFocus()
        }

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