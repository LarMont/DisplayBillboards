package com.example.displaybillboards.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseview.BaseViewModel
import com.example.core.adapters.BillboardAdapter
import com.example.core.getDBWorker
import com.example.core.getTaskManager
import com.example.core.models.Billboard
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
        getDBWorker(BaseApplication.getApplicationContext()).resaveBillboards(billboards ?: arrayListOf())
        isLocalData.set(false)
    }

    override suspend fun errorLoadingAsync(throwable: Throwable) {
        getDBWorker(BaseApplication.getApplicationContext()).getSavedBillboards().apply {
            liveData.postValue(this)
            isLocalData.set(true)
        }
    }
}