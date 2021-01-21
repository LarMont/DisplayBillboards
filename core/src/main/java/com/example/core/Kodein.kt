package com.example.core

import android.content.Context
import android.util.Log
import com.example.core.database.DBWorker
import com.example.core.database.DBWorkerImpl
import com.example.core.serverapi.BASE_URL
import com.example.core.serverapi.ServerApi
import com.example.core.serverapi.TaskManager
import com.example.core.serverapi.TaskManagerImpl
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class KodeinWorker {
    companion object {
        val kodein = ConfigurableKodein(mutable = true).apply {
            addConfig {
                bind<ServerApi>() with singleton { createRetrofit().create(ServerApi::class.java) }
                bind<TaskManager>() with singleton { TaskManagerImpl() }
                bind<DBWorker>() with multiton { context: Context -> DBWorkerImpl(context) }
            }
        }
    }
}

private inline fun <reified T : Any> kodeinResolve() = KodeinWorker.kodein.instance<T>()

fun getServerApi(): ServerApi = kodeinResolve()
fun getTaskManager(): TaskManager = kodeinResolve()
fun getDBWorker(context: Context): DBWorker = KodeinWorker.kodein.with(context).instance()

private fun createRetrofit() = Retrofit.Builder()   ////разнести по нужным местам
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(createHttpClient())
    .build()

var gson = GsonBuilder()
    .setLenient()
    .create()

private fun createHttpClient() = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .addInterceptor { chain ->
        val request = chain.request()
        val builder = request.newBuilder()
        val newRequest = builder.build()
        Log.e("retrofit_url", newRequest.url().toString())
        chain.proceed(newRequest)}
    .build()
