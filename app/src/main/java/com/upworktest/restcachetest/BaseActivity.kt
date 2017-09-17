package com.upworktest.restcachetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import com.upworktest.restcachetest.viewmodel.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.upworktest.restcachetest.utils.BindingUtils


abstract class BaseActivity : AppCompatActivity() {

    var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        BindingUtils.getBinder()?.bind(binding, createViewModel())
    }


    @NonNull
    protected abstract fun createViewModel(): ViewModel

    @LayoutRes
    protected abstract fun getLayoutId(): Int

}
