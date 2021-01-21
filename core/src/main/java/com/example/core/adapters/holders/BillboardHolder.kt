package com.example.core.adapters.holders

import android.view.ViewGroup
import com.example.baseview.BindableViewHolder
import com.example.core.R
import com.example.core.databinding.BillboardItemBinding

class BillboardHolder(parent: ViewGroup) :
    BindableViewHolder<BillboardItemBinding>(R.layout.billboard_item, parent) {
    fun setImagePath(imagePath: String) {
        binding?.imagePath = imagePath
    }
}