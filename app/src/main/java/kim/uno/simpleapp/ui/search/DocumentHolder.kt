package kim.uno.simpleapp.ui.search

import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import kim.uno.simpleapp.R
import kim.uno.simpleapp.data.dto.Document
import kim.uno.simpleapp.databinding.DocumentHolderBinding
import kim.uno.simpleapp.extension.setOnThrottleClickListener
import kim.uno.simpleapp.widget.recyclerview.AdvancedRecyclerViewAdapter
import kim.uno.simpleapp.widget.recyclerview.AdvancedViewHolder

open class DocumentHolder(
    adapter: AdvancedRecyclerViewAdapter,
    val favorite: (View, String) -> Unit,
    val documentDetail: (View, Document, FragmentNavigator.Extras) -> Unit
) : AdvancedViewHolder<Document>(adapter, R.layout.document_holder) {

    private val binding by lazy { DocumentHolderBinding.bind(itemView) }

    override fun onBindView(item: Document, position: Int) {
        super.onBindView(item, position)
        binding.item = item
        binding.root.setOnThrottleClickListener {
            documentDetail(it, item, FragmentNavigatorExtras(
                binding.container to binding.container.transitionName,
                binding.image to binding.image.transitionName
            ))
        }

        favorite(binding.favorite, item.isbn)
    }

}