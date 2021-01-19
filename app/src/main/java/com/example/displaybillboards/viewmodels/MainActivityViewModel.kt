package com.example.displaybillboards.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.displaybillboards.models.Billboard
import com.example.displaybillboards.utilities.getTaskManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val billboardsLiveData = MutableLiveData<ArrayList<Billboard>>()

    private var index = 0

    var clickListener: ((String) -> Unit)? = null

    fun onClick() {
        billboardsLiveData.value?.let {value ->
            clickListener?.invoke(value[index].poster)
            if (index < value.count() - 1) {
                index++
            } else {
                index = 0
            }
        }
    }

    fun fetchBillboards(){
        scope.launch {
            val billboards = getTaskManager().getBillboardsList()
            billboardsLiveData.postValue(billboards)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}