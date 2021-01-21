package com.example.baseview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<TModel : BaseViewModel<*>, TBinding : ViewDataBinding>(layoutResId: Int) :
    AppCompatActivity() {

    protected lateinit var viewModel: TModel
    protected val binding: TBinding by ActivityBinding<BaseActivity<TModel, TBinding>, TBinding>(
        layoutResId
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel()
        binding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
    }

    abstract fun createViewModel()

    protected inline fun <reified T : ViewModel> getViewModelFromProvider(): T =
        ViewModelProvider(this).get(T::class.java)
}