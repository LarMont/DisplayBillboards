package com.example.displaybillboards.utilities.serverapi

import android.app.Activity
import io.reactivex.Observable

interface ProgressTracker { ////Удалить лишнее, научиться юзать
    fun initCancel(callback: () -> Unit)
    fun start(loadingMessage: String)
    fun end()
    fun setProgress(progress: Float)
}

fun <T> Observable<T>.showProgress(activity: Activity, message: String): Observable<T> =
    showProgress(DefaultProgressTracker(activity), message)

fun <T> Observable<T>.showProgress(tracker: ProgressTracker, message: String,
                                   onCancel: (() -> Unit)? = null): Observable<T> {
    tracker.initCancel { onCancel ?: error("Запрос отменен") }
    return doOnSubscribe{tracker.start(message)}
        .doOnComplete { tracker.end() }
        .doOnError { tracker.end() }
}