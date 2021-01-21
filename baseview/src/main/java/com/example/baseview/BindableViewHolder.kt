package com.example.baseview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<TBinding : ViewDataBinding>(inflater: LayoutInflater, @LayoutRes layoutResId: Int, parent: ViewGroup?)
    : RecyclerView.ViewHolder(DataBindingUtil.inflate<TBinding>(inflater, layoutResId, parent, false).root) {
    val binding: TBinding? = DataBindingUtil.findBinding(itemView)

    constructor(@LayoutRes layoutResId: Int, parent: ViewGroup) : this(parent.getLayoutInflater(), layoutResId, parent)

}

private fun View.getLayoutInflater() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater