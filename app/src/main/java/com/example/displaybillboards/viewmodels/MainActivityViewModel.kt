package com.example.displaybillboards.viewmodels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
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
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val billboardsLiveData = MutableLiveData<ArrayList<Billboard>>()

    fun fetchBillboards(){
        scope.launch {
            val billboards: ArrayList<Billboard>? = getTaskManager().getBillboardsList()
            if (billboards != null) {
                billboardsLiveData.postValue(billboards)
                getDBWorker().resaveBillboards(billboards)
            } else {
                getDBWorker().getSavedBillboards().apply {
                    if (isNotEmpty()) {
                        Toast.makeText(BaseApplication.getApplicationContext(), "Загрузка с сервера не возможна, подгружены постеры с устройсва", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(BaseApplication.getApplicationContext(), "Постеры не разу не загружались", Toast.LENGTH_SHORT).show()
                    }
                    billboardsLiveData.postValue(this)
                }
            }
        }
    }

    val adapter = BillboardAdapter()
    lateinit var layoutManager: GridLayoutManager
    var toggleChecked = false
    val toggleListener: (Boolean) -> Unit = {
        toggleChecked = it
        adapter.setFilter(it)
    }

    fun updateBillboards() {
        billboardsLiveData.value?.let {
            adapter.setItems(it)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}