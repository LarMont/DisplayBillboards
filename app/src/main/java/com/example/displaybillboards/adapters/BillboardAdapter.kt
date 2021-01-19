package com.example.displaybillboards.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.displaybillboards.adapters.holders.BillboardHolder
import com.example.displaybillboards.models.Billboard

class BillboardAdapter : RecyclerView.Adapter<BillboardHolder>() {
    var items: ArrayList<Billboard> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillboardHolder =
        BillboardHolder(parent)

    override fun onBindViewHolder(holder: BillboardHolder, position: Int) {
        holder.setImagePath(items[position].poster)
    }

    override fun getItemCount(): Int = items.size
}