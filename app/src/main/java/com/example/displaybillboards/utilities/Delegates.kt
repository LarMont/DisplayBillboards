package com.example.displaybillboards.utilities

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KProperty

class ActivityBinding<in TClass : AppCompatActivity, out TBinding : ViewDataBinding>
    (@LayoutRes private val layoutResId: Int) {

    private var value: TBinding? = null

    operator fun getValue(thisRef: TClass, property: KProperty<*>): TBinding {
        return value ?: DataBindingUtil.setContentView<TBinding>(thisRef, layoutResId).apply {
            lifecycleOwner = thisRef
            value = this
        }
    }
}