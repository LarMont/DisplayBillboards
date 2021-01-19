package com.example.displaybillboards.utilities.bindable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.displaybillboards.utilities.functions.getLayoutInflater

abstract class BindableViewHolder<TBinding : ViewDataBinding>(inflater: LayoutInflater, @LayoutRes layoutResId: Int, parent: ViewGroup?)
    : RecyclerView.ViewHolder(DataBindingUtil.inflate<TBinding>(inflater, layoutResId, parent, false).root) {
    val binding: TBinding? = DataBindingUtil.findBinding(itemView)

    constructor(@LayoutRes layoutResId: Int, parent: ViewGroup) : this(parent.getLayoutInflater(), layoutResId, parent)
}