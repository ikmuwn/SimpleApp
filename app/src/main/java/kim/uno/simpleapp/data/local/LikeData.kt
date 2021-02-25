package kim.uno.simpleapp.data.local

import kim.uno.simpleapp.SimpleApp
import kim.uno.simpleapp.data.Result
import kim.uno.simpleapp.util.commitPref
import kim.uno.simpleapp.util.pref
import javax.inject.Inject

class LikeData @Inject constructor() {

    fun isFavorite(isbn: String): Result<Boolean> {
        val pref = SimpleApp.context.pref
        val likes = pref.getStringSet("likes", mutableSetOf<String>())?.toMutableSet()
        return Result.Success(likes?.contains(isbn) ?: false)
    }

    fun toggleFavorite(isbn: String): Result<Boolean> {
        var favorite = false
        SimpleApp.context.commitPref { pref, editor ->
            val likes = pref.getStringSet("likes", mutableSetOf<String>())?.toMutableSet() ?: mutableSetOf()
            favorite = if (likes.contains(isbn)) {
                likes.remove(isbn)
                false
            } else {
                likes.add(isbn)
                true
            }
            editor.putStringSet("likes", likes)
        }
        return Result.Success(favorite)
    }

    fun clearFavorite(): Result<Boolean> {
        return Result.Success(SimpleApp.context.commitPref { pref, editor ->
            editor.remove("likes")
        })
    }

}