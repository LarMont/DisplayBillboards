package com.example.billboardslibrary.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.billboardslibrary.R

@BindingAdapter("android:imagePath")
fun setImagePath(imageView: ImageView, imagePath: String) {
    if (imagePath.isNotEmpty()) {
        Glide.with(imageView)
            .load(imagePath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.image_not_found)
            .into(imageView)
    }
}