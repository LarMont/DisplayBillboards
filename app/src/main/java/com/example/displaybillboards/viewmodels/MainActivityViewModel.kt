package com.example.displaybillboards.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
import com.example.billboardslibrary.adapters.BillboardAdapter
import com.example.billboardslibrary.models.Billboard
import com.example.billboardslibrary.utilities.BaseViewModel
import com.example.billboardslibrary.utilities.getDBWorker
import com.example.billboardslibrary.utilities.getTaskManager
import com.example.displaybillboards.R
import com.example.displaybillboards.utilities.BaseApplication


class MainActivityViewModel : BaseViewModel<ArrayList<Billboard>>() {

    val adapter = BillboardAdapter()
    lateinit var layoutManager: GridLayoutManager
    var toggleChecked = false
    val isLocalData = ObservableBoolean()
    val statusLocalDataText = ObservableField<String>()
    val toggleListener: (Boolean) -> Unit = {
        toggleChecked = it
        adapter.setFilter(it)
    }

    fun updateBillboards() {
        liveData.value?.let {
            adapter.setItems(it)
            statusLocalDataText.set(
                if (it.isEmpty()) {
                    BaseApplication.getString(R.string.empty_local_data)
                } else {
                    BaseApplication.getString(R.string.load_local_data)
                }
            )
        }
    }

    override suspend fun loadDataAsync() {
        val billboards = getTaskManager().getBillboardsList()
        liveData.postValue(billboards)
        getDBWorker().resaveBillboards(billboards ?: arrayListOf())
        isLocalData.set(false)
    }

    override suspend fun errorLoadingAsync(throwable: Throwable) {
        getDBWorker().getSavedBillboards().apply {
            liveData.postValue(this)
            isLocalData.set(true)
        }
    }
}