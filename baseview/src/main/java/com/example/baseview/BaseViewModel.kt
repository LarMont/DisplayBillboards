package com.example.baseview

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T> : ViewModel(), LifecycleObserver {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = SupervisorJob(parentJob)

    private val scope = CoroutineScope(coroutineContext)

    val liveData = MutableLiveData<T>()

    abstract suspend fun loadDataAsync()

    open suspend fun errorLoadingAsync(throwable: Throwable) {
        Log.e("base_view_error", throwable.message.toString())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        fetchLiveData()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun destroy() {
        coroutineContext.cancel()
    }

    fun fetchLiveData() {
        scope.launch(getCoroutineExceptionHandler()) {
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