package com.example.displaybillboards.utilities

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    if(adapter != null) {
        recyclerView.adapter = adapter
    }
}