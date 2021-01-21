package com.example.billboardslibrary.utilities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.billboardslibrary.utilities.KodeinWorker.Companion.kodein

abstract class BaseActivity<TModel : BaseViewModel<*>, TBinding : ViewDataBinding>(layoutResId: Int) :
    AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.addImport(createContextModule(this), true)
    }

    protected lateinit var viewModel: TModel
    protected val binding: TBinding by ActivityBinding<BaseActivity<TModel, TBinding>, TBinding>(
        layoutResId
    )

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    protected inline fun <reified T : ViewModel> getViewModelFromProvider(): T =
        ViewModelProvider(this).get(T::class.java)
}