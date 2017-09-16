package com.upworktest.restcachetest.viewmodel;

import android.content.Intent;

import com.upworktest.restcachetest.TestApplication;
import com.upworktest.restcachetest.model.DataflowService;

public class ViewModel {
    public DataflowService dataService;

    public ViewModel() {
        dataService = TestApplication.getInstance().getApiService();
    }


    public void handleActivityResult(final int requestCode, int resultCode, Intent data) {
    }


    public void onPause() {
    }

    public void onResume() {
    }

}
