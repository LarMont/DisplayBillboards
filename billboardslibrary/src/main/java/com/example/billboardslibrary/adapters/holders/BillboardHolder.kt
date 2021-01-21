package com.example.billboardslibrary.adapters.holders

import android.view.ViewGroup
import com.example.billboardslibrary.R
import com.example.billboardslibrary.databinding.BillboardItemBinding
import com.example.billboardslibrary.utilities.bindable.BindableViewHolder

class BillboardHolder(parent: ViewGroup) :
    BindableViewHolder<BillboardItemBinding>(R.layout.billboard_item, parent) {
    fun setImagePath(imagePath: String) {
        binding?.imagePath = imagePath
    }
}