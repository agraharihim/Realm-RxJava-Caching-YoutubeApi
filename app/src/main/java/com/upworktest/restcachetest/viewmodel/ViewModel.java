package com.upworktest.restcachetest.viewmodel;


import com.upworktest.restcachetest.TestApplication;
import com.upworktest.restcachetest.model.DataflowService;

public class ViewModel {
    public DataflowService dataService;

    public ViewModel() {
        dataService = TestApplication.getInstance().getApiService();
    }

}
