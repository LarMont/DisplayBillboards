package com.example.displaybillboards.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.displaybillboards.adapters.BillboardAdapter
import com.example.displaybillboards.models.Billboard
import com.example.displaybillboards.utilities.getTaskManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val billboardsLiveData = MutableLiveData<ArrayList<Billboard>>()

    fun fetchBillboards(){
        scope.launch {
            val billboards = getTaskManager().getBillboardsList()
            billboardsLiveData.postValue(billboards)
        }
    }

    val adapter = BillboardAdapter()
    lateinit var layoutManager: GridLayoutManager

    fun updateBillboards() {
        billboardsLiveData.value?.let {
            adapter.items = it
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}