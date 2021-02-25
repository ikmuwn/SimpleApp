package kim.uno.simpleapp.ui.search

import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import kim.uno.simpleapp.R
import kim.uno.simpleapp.data.dto.Document
import kim.uno.simpleapp.data.local.LikeData
import kim.uno.simpleapp.databinding.DocumentHolderBinding
import kim.uno.simpleapp.widget.recyclerview.AdvancedRecyclerViewAdapter
import kim.uno.simpleapp.widget.recyclerview.AdvancedViewHolder

open class DocumentHolder(adapter: AdvancedRecyclerViewAdapter) :
    AdvancedViewHolder<Document>(adapter, R.layout.document_holder) {

    private val binding by lazy { DocumentHolderBinding.bind(itemView) }
    private val likeData by lazy { LikeData() }

    override fun onBindView(item: Document, position: Int) {
        super.onBindView(item, position)
        binding.item = item
        binding.root.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.container to binding.container.transitionName,
                binding.image to binding.image.transitionName
            )

            val direction = SearchFragmentDirections.documentDetail(document = item, index = position)
            it.findNavController().navigate(direction, extras)
        }

        binding.favorite.isSelected = likeData.isFavorite(item.isbn).data == true
    }

}