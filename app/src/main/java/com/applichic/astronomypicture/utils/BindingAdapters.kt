package com.applichic.astronomypicture.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.applichic.astronomypicture.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import androidx.swiperefreshlayout.widget.CircularProgressDrawable


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {

    if (!imageUrl.isNullOrEmpty()) {
        // Create a loader
        val drawable = CircularProgressDrawable(view.context)
        drawable.setColorSchemeColors(
            R.color.primary_color
        )
        drawable.centerRadius = 50f
        drawable.strokeWidth = 8f
        drawable.start();

        // Load the image
        Glide.with(view.context)
            .load(imageUrl)
            .thumbnail(0.25f)
            .placeholder(drawable)
            .error(R.drawable.ic_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}