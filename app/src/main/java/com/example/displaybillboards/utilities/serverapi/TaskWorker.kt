package com.example.displaybillboards.utilities.serverapi

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call

interface TaskWorker {
    fun <T> convertCallToObservable(call: Call<T>, onComplete: ((T) -> Unit), onError: ((Throwable) -> Unit)?): Observable<T>
}

class TaskWorkerImpl : TaskWorker {
    override fun <T> convertCallToObservable(call: Call<T>, onComplete: ((T) -> Unit), onError: ((Throwable) -> Unit)?): Observable<T> {
        val result = PublishSubject.create<T>()
        call.enqueue(RetrofitCallback(result))
        result.subscribe({ onComplete(it) }, { onError?.invoke(it) })
        return result
    }
}