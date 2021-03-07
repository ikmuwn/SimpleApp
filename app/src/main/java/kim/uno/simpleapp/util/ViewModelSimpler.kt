package kim.uno.simpleapp.util

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kim.uno.simpleapp.widget.recyclerview.AdvancedRecyclerViewAdapter

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}

@BindingAdapter("items")
fun items(recyclerView: RecyclerView, items: ObservableArrayList<*>) {
    val adapter = recyclerView.adapter
    if (adapter is AdvancedRecyclerViewAdapter)
        adapter.notifyDataSetChanged {
            it.clear()
            it.addAll(items = items)
        }
}