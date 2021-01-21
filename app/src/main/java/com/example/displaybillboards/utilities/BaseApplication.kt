package com.example.displaybillboards.utilities

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes

enum class ApplicationOrientation(val spanCount: Int) {
    PORTRAIT(3), LANDSCAPE(5)
}

class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        fun getApplicationContext() = context

        fun getString(@StringRes resId: Int) = context.getString(resId)

        fun getApplicationOrientation(): ApplicationOrientation =
            when (context.resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> ApplicationOrientation.PORTRAIT
                else -> ApplicationOrientation.LANDSCAPE
            }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}