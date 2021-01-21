package com.example.billboardslibrary.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance

class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        kodein.instance<ViewModel>(modelClass) as? T
            ?: modelClass.newInstance()
}