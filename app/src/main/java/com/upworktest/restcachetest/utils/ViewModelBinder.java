package com.upworktest.restcachetest.utils;

import android.databinding.ViewDataBinding;

import com.upworktest.restcachetest.viewmodel.ViewModel;


public interface ViewModelBinder {
    void bind(ViewDataBinding viewDataBinding, ViewModel viewModel);
}
