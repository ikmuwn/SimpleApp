package kim.uno.simpleapp.data.dto

import android.os.Parcelable
import android.text.TextUtils
import kim.uno.simpleapp.widget.recyclerview.ItemDiff
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Document(
    val authors: List<String>,
    val contents: String,
    val datetime: String,

    @ItemDiff
    val isbn: String,
    val price: Int,
    val publisher: String,
    val sale_price: Int,
    val status: String,
    val thumbnail: String,
    val title: String,
    val translators: List<String>,
    val url: String
) : Parcelable {
    val formattedDate: String
        get() {
            return try {
                val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(datetime)
                SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(date)
            } catch (e: Throwable) {
                ""
            }
        }

    val formattedPrice: String
        get() {
            val price = if (sale_price > 0) sale_price else price
            return String.format("%,d원", price)
        }

    val author: String
        get() = TextUtils.join(", ", authors)
}