package com.example.displaybillboards.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.displaybillboards.adapters.holders.BillboardHolder

class BillboardAdapter : RecyclerView.Adapter<BillboardHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillboardHolder =
        BillboardHolder(parent)

    override fun onBindViewHolder(holder: BillboardHolder, position: Int) {
        holder.setModel()
    }

    override fun getItemCount(): Int = 0
}