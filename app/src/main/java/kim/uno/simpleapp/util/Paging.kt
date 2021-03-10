package kim.uno.simpleapp.util

class Paging(val request: () -> Unit) {

    var page = 0
    var loaded = 0
    var isEnded = false

    fun refresh() {
        page = 0
        loaded = 0
        isEnded = false
        load()
    }

    fun load(page: Int = loaded + 1) {
        if (this.page != 0 || isEnded) return
        this.page = page
        request()
    }

    fun success(end: Boolean) {
        loaded = page
        page = 0
        isEnded = end
    }

    fun error() {
        page = 0
    }

}