package com.applichic.astronomypicture.utils

import android.net.Uri
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.applichic.astronomypicture.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.model.MediaType
import com.github.piasy.biv.view.BigImageView


@BindingAdapter("imageFromEntry")
fun bindEntryImage(view: ImageView, entry: Entry?) {
    // Create a loader
    val drawable = CircularProgressDrawable(view.context)
    drawable.setColorSchemeColors(
        R.color.primary_color
    )
    drawable.centerRadius = 50f
    drawable.strokeWidth = 8f
    drawable.start()

    if (entry != null) {
        // Displays the image
        if (entry.mediaType == MediaType.Image && entry.url.isNotEmpty()) {
            Glide.with(view.context)
                .load(entry.url)
                .placeholder(drawable)
                .error(R.drawable.ic_broken_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }

        // Displays the video's thumbnails
        if (entry.mediaType == MediaType.Video && !entry.thumbnailUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(entry.thumbnailUrl)
                .placeholder(drawable)
                .error(R.drawable.ic_broken_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}

@BindingAdapter("url", "hdUrl")
fun bindEntryPreviewImage(view: BigImageView, url: String, hdUrl: String?) {
    if (hdUrl != null) {
        view.showImage(Uri.parse(url), Uri.parse(hdUrl))
    } else {
        view.showImage(Uri.parse(url))
    }
}

@BindingAdapter("url")
fun bingUrlWebView(view: WebView, url: String) {
    view.settings.javaScriptEnabled = true

    if(view.url == null) {
        view.loadUrl(url)
    }
}