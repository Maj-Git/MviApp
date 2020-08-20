package com.example.mviapp.data.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, url: String) {
        var requestOptions = RequestOptions()
    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(12))

    Glide.with(imageView.context)
        .load(url)
        .apply(requestOptions)
        .into(imageView)
}