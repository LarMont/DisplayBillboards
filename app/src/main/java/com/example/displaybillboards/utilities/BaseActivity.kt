package com.example.displaybillboards.utilities

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.displaybillboards.utilities.KodeinWorker.Companion.kodein

abstract class BaseActivity<TModel : ViewModel, TBinding : ViewDataBinding>(layoutResId: Int) :
    AppCompatActivity() {
    protected lateinit var viewModel: TModel
    protected val binding: TBinding by ActivityBinding<BaseActivity<TModel, TBinding>, TBinding>(
        layoutResId
    )

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    protected inline fun <reified T : ViewModel> getViewModelFromProvider(): T =
        ViewModelProvider(this, ViewModelFactory(kodein)).get(T::class.java)

//    abstract fun getViewModelClass(): Class<TModel>           ////подумать и возможно реализовать через это
}