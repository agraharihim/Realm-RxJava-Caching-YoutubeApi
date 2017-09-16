package com.upworktest.restcachetest.viewmodel;

import android.databinding.ObservableField;

public class InternetErrorViewModel extends ViewModel {

    public final ObservableField<String> offlineText = new ObservableField<>("");

    public InternetErrorViewModel() {
        offlineText.set("Internet connection not available \nSaved Queries : \n" + dataService.getSavedQueries());
    }
}
