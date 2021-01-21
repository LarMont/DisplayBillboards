package com.example.displaybillboards.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.displaybillboards.R
import com.example.displaybillboards.adapters.BillboardAdapter
import com.example.displaybillboards.models.Billboard
import com.example.displaybillboards.utilities.BaseApplication
import com.example.displaybillboards.utilities.getDBWorker
import com.example.displaybillboards.utilities.getTaskManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel : ViewModel() { ////Сделать общую модель
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = SupervisorJob(parentJob)

    private val scope = CoroutineScope(coroutineContext)

    val billboardsLiveData = MutableLiveData<ArrayList<Billboard>>()

    fun fetchBillboards(){
        scope.launch(CoroutineExceptionHandler { context, throwable ->
            scope.launch {
                getDBWorker().getSavedBillboards().apply {
                    billboardsLiveData.postValue(this)
                    isLocalData.set(true)
                }
            }
        }) {
            val billboards = getTaskManager().getBillboardsList()
            billboardsLiveData.postValue(billboards)
            getDBWorker().resaveBillboards(billboards ?: arrayListOf())
            isLocalData.set(false)
        }
    }

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
        billboardsLiveData.value?.let {
            adapter.setItems(it)
            statusLocalDataText.set(if (it.isEmpty()) {
                BaseApplication.getString(R.string.empty_local_data)
            } else {
                BaseApplication.getString(R.string.load_local_data)
            })
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}