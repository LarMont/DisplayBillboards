package com.example.displaybillboards.adapters.holders

import android.view.ViewGroup
import com.example.displaybillboards.R
import com.example.displaybillboards.databinding.BillboardItemBinding
import com.example.displaybillboards.utilities.bindable.BindableViewHolder

class BillboardHolder(parent: ViewGroup) :
    BindableViewHolder<BillboardItemBinding>(R.layout.billboard_item, parent) {
    fun setImagePath(imagePath: String) {
        binding?.imagePath = imagePath
    }
}