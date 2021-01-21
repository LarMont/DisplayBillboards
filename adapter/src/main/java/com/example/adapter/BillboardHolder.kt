package com.example.adapter

import android.view.ViewGroup
import com.example.adapter.databinding.BillboardItemBinding
import com.example.baseview.BindableViewHolder

class BillboardHolder(parent: ViewGroup) :
    BindableViewHolder<BillboardItemBinding>(R.layout.billboard_item, parent) {
    fun setImagePath(imagePath: String) {
        binding?.imagePath = imagePath
    }
}