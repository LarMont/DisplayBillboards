package com.example.displaybillboards.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.displaybillboards.R
import com.example.displaybillboards.databinding.ActivityMainBinding
import com.example.displaybillboards.utilities.BaseActivity
import com.example.displaybillboards.viewmodels.MainActivityViewModel

class MainActivity :
    BaseActivity<MainActivityViewModel, ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModelFromProvider()
        viewModel.fetchBillboards()
        viewModel.billboardsLiveData.observe(this,  Observer {
            viewModel.updateBillboards()
        })
        binding.model = viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelAllRequests()
    }
}