package com.example.displaybillboards.utilities.serverapi

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.HttpURLConnection

abstract class RetrofitFileCallback(private val subject: PublishSubject<File>,
                                    private val tracker: ProgressTracker? = null) :
    Callback<FileSaver> {
    override fun onFailure(call: Call<FileSaver>?, t: Throwable?) {
        t?.let {
            subject.onError(t)
        }
    }

    override fun onResponse(call: Call<FileSaver>?, response: Response<FileSaver>?) {
        val responseBody = response?.body()
        when {
            response?.code() != HttpURLConnection.HTTP_OK ->
                onFailure(call, Exception(response?.code().toString()))
            responseBody == null -> onFailure(call, Exception())
            else -> onResult(call, responseBody)
        }
    }

    private fun onResult(call: Call<FileSaver>?, saver: FileSaver) {
        Observable.just(saver)
            .subscribeOn(Schedulers.newThread())
            .map { it.save(getFileType(), getFileName(), tracker) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ file ->
                if (file != null) {
                    subject.onNext(file)
                } else {
                    onFailure(call, Exception())
                }
            }, {
                onFailure(call, it)
            })
    }
    abstract fun getFileType(): FileType
    abstract fun getFileName(): String
}