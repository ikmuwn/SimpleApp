package kim.uno.simpleapp.widget.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class AdvancedViewHolder<ITEM>(val adapter: AdvancedRecyclerViewAdapter, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    constructor(adapter: AdvancedRecyclerViewAdapter, resId: Int)
            : this(adapter, LayoutInflater.from(adapter.recyclerView!!.context)
        .inflate(resId, adapter.recyclerView, false))

    open fun onBindView(item: ITEM, position: Int) { }
    open fun onBindView(item: ITEM, position: Int, payloads: MutableList<Any>?) { }

    val context: Context
        get() = itemView.context

}