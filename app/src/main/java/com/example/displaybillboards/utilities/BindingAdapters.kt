package com.example.displaybillboards.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("android:adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    if(adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("android:layoutManager")
fun setLayoutManager(recyclerView: RecyclerView, manager: RecyclerView.LayoutManager?) {
    if(manager != null) {
        recyclerView.layoutManager = manager
    }
}

@BindingAdapter("android:imagePath")
fun setImagePath(imageView: ImageView, imagePath: String) {
    if (imagePath.isNotEmpty()) {
        Glide.with(imageView)
            .load(imagePath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}