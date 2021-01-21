package com.example.core.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.adapters.holders.BillboardHolder
import com.example.core.models.Billboard

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
        holder.setImagePath(itemsToView[position].poster)
    }

    override fun getItemCount(): Int = itemsToView.size

    override fun getItemId(position: Int): Long {
        return itemsToView[position].id.toLong()
    }

    private fun updateItemsToView() {       ////Какой то очень корявый фильтр
        items.forEachIndexed { index, billboard ->
            if (itemsToView.contains(billboard)) {
                if (isUsedFilter) {
                    if (billboard.year != filterYear) {
                        val deletedIndex = itemsToView.indexOf(billboard)
                        itemsToView.remove(billboard)
                        notifyItemRemoved(deletedIndex)
                    }
                }
            } else {
                if (isUsedFilter) {
                    if (billboard.year == filterYear) {
                        itemsToView.add(billboard)
                        notifyItemInserted(itemsToView.size - 1)
                    }
                } else {
                    itemsToView.add(index, billboard)
                    notifyItemRangeChanged(index, itemsToView.size - 1)
                }
            }
        }
    }
}