package com.example.displaybillboards.utilities.serverapi

import com.example.displaybillboards.models.Billboard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

const val BASE_URL = "https://raw.githubusercontent.com/ar2code/apitest/master/"

interface ServerApi {
    @GET("movies.json")
    fun getBillboardsList(): Call<ArrayList<Billboard>>

    @Streaming
    @GET("{imagePath}")
    fun getBillboardImage(@Path("imagePath") imagePath: String): Call<FileSaver>
}