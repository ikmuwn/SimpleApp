package kim.uno.simpleapp.ui.search

import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import kim.uno.simpleapp.data.dto.Document
import kim.uno.simpleapp.widget.recyclerview.AdvancedRecyclerViewAdapter
import kim.uno.simpleapp.widget.recyclerview.AdvancedViewHolder

class SearchAdapter(
    val binder: (Int) -> Unit,
    val favorite: (View, String) -> Unit,
    val documentDetail: (View, Document, FragmentNavigator.Extras) -> Unit
) : AdvancedRecyclerViewAdapter() {

    override fun onCreateHolder(viewType: Int): AdvancedViewHolder<*> {
        return DocumentHolder(this, favorite, documentDetail)
    }

    override fun onBindViewHolder(holder: AdvancedViewHolder<Any>, position: Int) {
        super.onBindViewHolder(holder, position)
        binder(position)
    }

}