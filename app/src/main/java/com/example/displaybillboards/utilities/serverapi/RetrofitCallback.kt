package com.example.displaybillboards.utilities.serverapi

import android.util.Log
import com.example.displaybillboards.constants.ERROR_LOG_TAG
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

open class RetrofitCallback<T>(private val subject: PublishSubject<T>) : Callback<T> {
    override fun onFailure(call: Call<T>?, t: Throwable?) {
        t?.let {
            subject.onError(t)
        }
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        val responseBody = response?.body()
        when {
            response?.code() != HttpURLConnection.HTTP_OK -> Log.e(ERROR_LOG_TAG,"Callback error: ${response?.code() ?: 0}" )
            responseBody == null -> Log.e(ERROR_LOG_TAG,"Callback null error" )
            else -> onResult(responseBody)
        }
    }

    open fun onResult(value: T) {
        subject.onNext(value)
        subject.onComplete()
    }
}