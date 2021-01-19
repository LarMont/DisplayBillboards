package com.example.displaybillboards.utilities.serverapi

import com.example.displaybillboards.models.Billboard
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

const val BASE_URL = "https://raw.githubusercontent.com/ar2code/apitest/master/"

interface ServerApi {
    @GET("movies.json")
    fun getBillboardsListAsync(): Deferred<Response<ArrayList<Billboard>>>
}