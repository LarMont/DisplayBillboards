package com.example.displaybillboards.utilities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.displaybillboards.activities.REQUEST_CODE_WORK_WITH_EXTERNAL_STORAGE
import com.example.displaybillboards.utilities.KodeinWorker.Companion.kodein

abstract class BaseActivity<TModel : ViewModel, TBinding : ViewDataBinding>(layoutResId: Int) :
    AppCompatActivity() {
    protected lateinit var viewModel: TModel
    protected val binding: TBinding by ActivityBinding<BaseActivity<TModel, TBinding>, TBinding>(
        layoutResId
    )

    override fun onResume() {
        super.onResume()

        if (!verifyNeededPermissions()) {
            errorPermissionHandler()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    protected inline fun <reified T : ViewModel> getViewModelFromProvider(): T =
        ViewModelProvider(this, ViewModelFactory(kodein)).get(T::class.java)

    abstract fun errorPermissionHandler()

    private val neededPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun verifyNeededPermissions(): Boolean {
        return if (!isNeededPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                neededPermissions,
                REQUEST_CODE_WORK_WITH_EXTERNAL_STORAGE
            )
            false
        } else {
            true
        }
    }

    private fun isNeededPermissionsGranted(): Boolean {
        return neededPermissions.all { permission ->
            ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

//    abstract fun getViewModelClass(): Class<TModel>
}