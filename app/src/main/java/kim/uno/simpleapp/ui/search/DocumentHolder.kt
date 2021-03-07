package kim.uno.simpleapp.ui.search

import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import dagger.hilt.android.scopes.FragmentScoped
import kim.uno.simpleapp.R
import kim.uno.simpleapp.data.dto.Document
import kim.uno.simpleapp.databinding.DocumentHolderBinding
import kim.uno.simpleapp.widget.recyclerview.AdvancedRecyclerViewAdapter
import kim.uno.simpleapp.widget.recyclerview.AdvancedViewHolder

@FragmentScoped
open class DocumentHolder(adapter: AdvancedRecyclerViewAdapter) :
    AdvancedViewHolder<Document>(adapter, R.layout.document_holder) {

    private val binding by lazy { DocumentHolderBinding.bind(itemView) }

    override fun onBindView(item: Document, position: Int) {
        super.onBindView(item, position)
        binding.item = item
        binding.root.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.container to binding.container.transitionName,
                binding.image to binding.image.transitionName
            )

            val direction =
                SearchFragmentDirections.documentDetail(document = item, index = position)
            it.findNavController().navigate(direction, extras)
        }

        (adapter as SearchAdapter).favorite(binding.favorite, item.isbn)
    }

}