package kim.uno.simpleapp.widget.recyclerview

import androidx.recyclerview.widget.DiffUtil

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ItemDiff

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentsDiff

class AdvancedRecyclerViewDiffCallback(
        private val before: ArrayList<Pair<Int, Any>>,
        private val after: ArrayList<Pair<Int, Any>>
) : DiffUtil.Callback() {

    override fun getOldListSize() = before.size
    override fun getNewListSize() = after.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areSame(oldItemPosition, newItemPosition, ItemDiff::class.java, false)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areSame(oldItemPosition, newItemPosition, ContentsDiff::class.java, true)
    }

    private fun areSame(oldItemPosition: Int, newItemPosition: Int, clazz: Class<out Annotation>, default: Boolean): Boolean {
        val before = before[oldItemPosition]
        val after = after[newItemPosition]
        if (before.second.javaClass.name == after.second.javaClass.name && before.first == after.first) {
            var isAnnotationPresent = default
            before.second.javaClass.declaredFields
                    .filter { it.isAnnotationPresent(clazz) }
                    .forEach {
                        isAnnotationPresent = true

                        it.isAccessible = true
                        val beforeValue = it.get(before.second)
                        val afterValue = it.get(after.second)
                        it.isAccessible = false

                        if (beforeValue != afterValue) {
                            return false
                        }
                    }

            return !isAnnotationPresent || before == after
        }

        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val before = before[oldItemPosition]
        val after = after[newItemPosition]
        if (before.second.javaClass.name == after.second.javaClass.name) {
            val payload = ArrayList<String>()
            before.second.javaClass.declaredFields
                    .filter { it.isAnnotationPresent(ContentsDiff::class.java) }
                    .forEach {
                        it.isAccessible = true
                        val beforeValue = it.get(before.second)
                        val afterValue = it.get(after.second)
                        if (beforeValue != afterValue) payload.add(it.name)
                        it.isAccessible = false
                    }

            if (payload.size > 0) return payload
        }
        return null
    }

}