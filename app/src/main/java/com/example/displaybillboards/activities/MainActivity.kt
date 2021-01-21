package com.example.displaybillboards.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseview.BaseActivity
import com.example.displaybillboards.utilities.BaseApplication
import com.example.displaybillboards.R
import com.example.displaybillboards.databinding.ActivityMainBinding
import com.example.displaybillboards.viewmodels.MainActivityViewModel

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModelFromProvider()
        viewModel.fetchLiveData()
        viewModel.liveData.observe(this,  Observer {
            viewModel.updateBillboards()
        })
        binding.model = viewModel
    }

    override fun onStart() {
        super.onStart()

        viewModel.layoutManager = GridLayoutManager(this, BaseApplication.getApplicationOrientation().spanCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelAllRequests()
    }
}