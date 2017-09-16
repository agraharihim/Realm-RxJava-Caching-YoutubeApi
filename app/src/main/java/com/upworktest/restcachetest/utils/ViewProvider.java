package com.upworktest.restcachetest.utils;

import android.support.annotation.LayoutRes;

import com.upworktest.restcachetest.viewmodel.ViewModel;

public interface ViewProvider {
    @LayoutRes
    int getView(ViewModel vm);
}
