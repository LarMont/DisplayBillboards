package com.example.displaybillboards.utilities

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.database.CursorWindow
import android.graphics.drawable.Drawable
import android.net.wifi.WifiManager
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.displaybillboards.R

class BaseApplication : Application(){  ////По завершению удалить лишнее

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        fun getApplicationContext() = context
        fun getString(@StringRes resId: Int) = context.getString(resId) ?: ""

        fun getString(@StringRes resId: Int, vararg text: Any) = context.getString(resId, *text) ?: ""

        fun getColor(@ColorRes resId: Int) = context.resources?.getColor(resId) ?: 0

        fun getDrawable(@DrawableRes resId: Int): Drawable = context.resources.getDrawable(resId)

        fun getInteger(@IntegerRes resId: Int) = context.resources?.getInteger(resId) ?: 0

        fun showFeatureIsDevelopingMessage() {
            context.let {cnt ->
                Toast.makeText(cnt, R.string.message_developing, Toast.LENGTH_LONG).show()
            }
        }
//        fun isNeededPermissionsGranted() = context.isNeededPermissionsGranted()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}