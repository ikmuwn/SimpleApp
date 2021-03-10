package kim.uno.simpleapp.extension

import android.app.Service
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun View.snackbar(text: Int, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, context.getString(text), length).show()
}

fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.toast(text: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(text), length).show()
}

fun Fragment.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(text, length)
}

fun Fragment.toast(text: Int, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(text, length)
}

@BindingAdapter("visibleByText")
fun viewVisibleByText(view: View, string: String?) {
    view.visible = !string.isNullOrBlank()
}

@BindingAdapter("isVisible")
fun isVisible(view: View, visible: Boolean) {
    view.visible = visible
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.showKeyboard() {
    (context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    (context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

@BindingAdapter("textFuture")
fun textFuture(textView: TextView, text: String?) {
    when (textView) {
        is AppCompatTextView -> textView.setTextFuture(text)
        is AppCompatEditText -> textView.setTextFuture(text)
    }
}

@BindingAdapter("textResFuture")
fun textFuture(textView: TextView, resId: Int) {
    textFuture(textView, textView.context.getString(resId))
}

fun AppCompatTextView.setTextFuture(text: String?) =
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text ?: "",
            TextViewCompat.getTextMetricsParams(this),
            null
        )
    )

fun AppCompatEditText.setTextFuture(text: String?) =
    setText(
        PrecomputedTextCompat.create(
            text ?: "",
            TextViewCompat.getTextMetricsParams(this)
        )
    )

fun EditText.setOnEditorAction(unit: (EditText) -> Unit) {
    setOnEditorActionListener { textView, action, event ->
        if (action == imeOptions) {
            unit(this)
            true
        } else
            false
    }
}

@BindingAdapter("isSelected")
fun isSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}

@BindingAdapter("isRefreshing")
fun isRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}