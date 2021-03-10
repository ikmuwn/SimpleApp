package kim.uno.simpleapp.extension

import android.content.Context
import android.content.SharedPreferences
import kim.uno.simpleapp.BuildConfig

val Context.pref: SharedPreferences
    get() = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

fun Context.commitPref(unit: (SharedPreferences, SharedPreferences.Editor) -> Unit): Boolean {
    val editor = pref.edit()
    unit(pref, editor)
    return editor.commit()
}

