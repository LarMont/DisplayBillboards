package com.example.billboardslibrary.utilities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.billboardslibrary.constants.ERROR_LOG_TAG
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T> : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = SupervisorJob(parentJob)

    private val scope = CoroutineScope(coroutineContext)

    val liveData = MutableLiveData<T>()

    abstract suspend fun loadDataAsync()

    open suspend fun errorLoadingAsync(throwable: Throwable) {
        Log.e(ERROR_LOG_TAG, throwable.message.toString())
    }

    fun fetchLiveData() {
        scope.launch (getCoroutineExceptionHandler()) {
            loadDataAsync()
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

    private fun getCoroutineExceptionHandler() = CoroutineExceptionHandler { _, throwable ->
        scope.launch {
            errorLoadingAsync(throwable)
        }
    }
}