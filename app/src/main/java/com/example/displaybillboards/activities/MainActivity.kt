package com.example.displaybillboards.activities

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.displaybillboards.R
import com.example.displaybillboards.databinding.ActivityMainBinding
import com.example.displaybillboards.utilities.BaseActivity
import com.example.displaybillboards.viewmodels.MainActivityViewModel

const val REQUEST_CODE_WORK_WITH_EXTERNAL_STORAGE = 1

class MainActivity :

    BaseActivity<MainActivityViewModel, ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModelFromProvider()
        viewModel.fetchBillboards()
        viewModel.clickListener = {
            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)
        }
        binding.model = viewModel
    }

    override fun errorPermissionHandler() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelAllRequests()
    }
}