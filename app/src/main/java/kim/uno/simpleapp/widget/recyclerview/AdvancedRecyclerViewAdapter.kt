package kim.uno.simpleapp.widget.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class AdvancedRecyclerViewAdapter : RecyclerView.Adapter<AdvancedViewHolder<Any>>() {

    var recyclerView: RecyclerView? = null
    var items = ArrayList<Pair<Int, Any>>()

    @Suppress("UNCHECKED_CAST")
    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdvancedViewHolder<Any> =
        onCreateHolder(viewType) as AdvancedViewHolder<Any>

    abstract fun onCreateHolder(viewType: Int): AdvancedViewHolder<*>

    override fun getItemCount() = items.size

    fun get(position: Int) = items.getOrNull(position)

    fun getItem(position: Int) = items[position].second

    override fun onBindViewHolder(holder: AdvancedViewHolder<Any>, position: Int) {
        holder.onBindView(getItem(position), position)
    }

    override fun onBindViewHolder(
        holder: AdvancedViewHolder<Any>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.onBindView(getItem(position), position, payloads)
    }

    override fun getItemViewType(position: Int): Int = items[position].first

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    fun notifyDataSetChanged(
        detectMoves: Boolean = true,
        unit: (AdvancedRecyclerViewAdapter) -> Unit
    ): AdvancedRecyclerViewAdapter {
        val transactionPairs = ArrayList(items)
        unit(this)

        if (transactionPairs.size == 0) {
            notifyDataSetChanged()
        } else {
            DiffUtil.calculateDiff(
                AdvancedRecyclerViewDiffCallback(transactionPairs, items),
                detectMoves
            ).dispatchUpdatesTo(this)
        }

        return this@AdvancedRecyclerViewAdapter
    }

    open fun add(index: Int = items.size, item: Any? = null, viewType: Int = 0) {
        items.add(index, viewType to (item ?: Any()))
    }

    open fun add(index: Int = items.size, pair: Pair<Int, Any>) {
        items.add(index, pair)
    }

    fun addAll(index: Int = this.items.size, items: ArrayList<*>?, viewType: Int = 0) {
        items?.forEachIndexed { i, item ->
            add(
                index = index + i,
                item = item,
                viewType = viewType
            )
        }
    }

    fun remove(item: Any): Boolean {
        return items.firstOrNull {
            it.second == item
        }?.let {
            items.remove(it)
        } ?: false
    }

    fun removeAt(index: Int): Boolean {
        val hasIndex = index > -1 && index < items.size
        if (hasIndex) items.removeAt(index)
        return hasIndex
    }

    fun contains(item: Any): Boolean {
        return items.firstOrNull { it.second == item } != null
    }

    open fun clear() {
        items.clear()
    }

}