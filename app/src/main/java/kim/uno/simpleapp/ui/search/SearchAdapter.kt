package kim.uno.simpleapp.ui.search

import kim.uno.simpleapp.widget.recyclerview.AdvancedRecyclerViewAdapter
import kim.uno.simpleapp.widget.recyclerview.AdvancedViewHolder

class SearchAdapter(val binder: (Int) -> Unit) : AdvancedRecyclerViewAdapter() {

    override fun onCreateHolder(viewType: Int): AdvancedViewHolder<*> {
        return DocumentHolder(this)
    }

    override fun onBindViewHolder(holder: AdvancedViewHolder<Any>, position: Int) {
        super.onBindViewHolder(holder, position)
        binder(position)
    }

}