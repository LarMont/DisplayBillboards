package com.example.displaybillboards.utilities.serverapi

import com.example.displaybillboards.utilities.functions.getFileSaver
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *> {
        val adapter: TypeAdapter<out Any>? = gson.getAdapter(TypeToken.get(type))
        return if ((type as? Class<*>)?.isAssignableFrom(FileSaver::class.java) == true) getFileConverter() else ResponseBodyConverter(
            gson,
            adapter ?: throw Exception("ConverterFactory error")
        )
    }

    private fun getFileConverter() =
        Converter<ResponseBody, FileSaver> { responseBody ->
            responseBody.byteStream().getFileSaver(responseBody.contentLength())
        }
}

class ResponseBodyConverter(private val gson: Gson, private val adapter: TypeAdapter<out Any>) :
    Converter<ResponseBody, Any> {
    override fun convert(value: ResponseBody?): Any? {
        val jsonReader = gson.newJsonReader(value?.charStream())
        return value.use {
            adapter.read(jsonReader)
        }
    }
}