package com.example.displaybillboards.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.displaybillboards.adapters.holders.BillboardHolder
import com.example.displaybillboards.models.Billboard

class BillboardAdapter : RecyclerView.Adapter<BillboardHolder>() {

    private val filterYear = "2020"

    private var isUsedFilter: Boolean = false

    private var items: ArrayList<Billboard> = arrayListOf()

    private var itemsToView: ArrayList<Billboard> = arrayListOf()

    fun setFilter(isUsedFilter: Boolean) {
        this.isUsedFilter = isUsedFilter
        updateItemsToView()
    }

    fun setItems(billboards: ArrayList<Billboard>) {
        items = billboards
        updateItemsToView()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillboardHolder =
        BillboardHolder(parent)

    override fun onBindViewHolder(holder: BillboardHolder, position: Int) {
        holder.setImagePath(items[position].poster)
    }

    override fun getItemCount(): Int = itemsToView.size

    private fun updateItemsToView() {
        items.forEach {
            if (itemsToView.contains(it)) {
                if (isUsedFilter) {
                    if (it.year != filterYear) {
                        val position = itemsToView.indexOf(it)
                        itemsToView.remove(it)
                        notifyItemRemoved(position)
                    }
                }
            } else {
                if (isUsedFilter) {
                    if (it.year == filterYear) {
                        itemsToView.add(it)
                        notifyItemInserted(itemsToView.size - 1)
                    }
                } else {
                    itemsToView.add(it)
                    notifyItemInserted(itemsToView.size - 1)

                }
            }
        }
    }
}