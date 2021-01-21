package com.example.displaybillboards.utilities

import android.view.View
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    if (adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("android:layoutManager")
fun setLayoutManager(recyclerView: RecyclerView, manager: RecyclerView.LayoutManager?) {
    if (manager != null) {
        recyclerView.layoutManager = manager
    }
}

@BindingAdapter("android:toggleListener")
fun setToggleListener(toggleButton: ToggleButton, listener: ((Boolean) -> Unit)?) {
    toggleButton.setOnCheckedChangeListener { _, isChecked ->
        listener?.invoke(isChecked)
    }
}

@BindingAdapter("android:visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}