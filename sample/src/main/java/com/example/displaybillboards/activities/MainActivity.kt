package com.example.displaybillboards.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseview.BaseActivity
import com.example.displaybillboards.R
import com.example.displaybillboards.databinding.ActivityMainBinding
import com.example.displaybillboards.utilities.BaseApplication
import com.example.displaybillboards.viewmodels.MainActivityViewModel

class MainActivity :
    BaseActivity<MainActivityViewModel, ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = viewModel
    }

    override fun createViewModel() {
        viewModel = getViewModelFromProvider()
        viewModel.liveData.observe(this, Observer {
            viewModel.updateBillboards()
        })
        viewModel.updateLayoutManager(
            GridLayoutManager(
                this,
                BaseApplication.getApplicationOrientation().spanCount
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelAllRequests()
    }
}