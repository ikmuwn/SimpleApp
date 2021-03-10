package kim.uno.simpleapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.setImageBitmap(null)
    Glide.with(imageView.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun <T> RequestBuilder<T>.finally(unit: (Boolean) -> Boolean): RequestBuilder<T> {
    listener(object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            return unit(false)
        }

        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return unit(false)
        }
    })
    return this
}


