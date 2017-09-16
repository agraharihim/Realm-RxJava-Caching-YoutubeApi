package com.upworktest.restcachetest

import com.upworktest.restcachetest.viewmodel.HomeViewModel
import com.upworktest.restcachetest.viewmodel.ViewModel

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createViewModel(): ViewModel {
        return HomeViewModel()
    }

}
