package com.example.displaybillboards.utilities

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.displaybillboards.constants.RETROFIT_LOG_TAG
import com.example.displaybillboards.utilities.serverapi.*
import com.example.displaybillboards.viewmodels.MainActivityViewModel
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
                bindViewModels()
            }
        }

        private fun Kodein.Builder.bindViewModels() {
            bindViewModel<MainActivityViewModel>() with provider {
                MainActivityViewModel()
            }
        }

        private inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel() =
            this.bind<ViewModel>(T::class.java)
    }
}

private inline fun <reified T : Any> kodeinResolve() = KodeinWorker.kodein.instance<T>()

fun getServerApi(): ServerApi = kodeinResolve()
fun getTaskManager(): TaskManager = kodeinResolve()

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
        Log.e(RETROFIT_LOG_TAG, newRequest.url().toString())
        chain.proceed(newRequest)}
    .build()