package com.upworktest.restcachetest.viewmodel;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.upworktest.restcachetest.R;
import com.upworktest.restcachetest.model.YtData;
import com.upworktest.restcachetest.utils.FieldUtils;
import com.upworktest.restcachetest.utils.ViewProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


public class HomeViewModel extends ViewModel {

    public final ViewProvider itemViewProvider;
    public final ObservableField<String> searchQuery = new ObservableField<>("");
    public final Observable<List<ViewModel>> items;

    public HomeViewModel() {

        itemViewProvider = new ViewProvider() {
            @Override
            public int getView(ViewModel vm) {
                if (vm instanceof YtItemViewModel)
                    return R.layout.item_feed;
                if (vm instanceof LoadingItemViewModel)
                    return R.layout.item_loading;
                if (vm instanceof InternetErrorViewModel)
                    return R.layout.item_internet_issue;
                if (vm instanceof EmptyDataViewModel)
                    return R.layout.item_empty_results;
                if (vm instanceof SearchYtViewModel)
                    return R.layout.item_type_query;
                return 0;
            }
        };

        items = FieldUtils.toObservable(searchQuery)
                .debounce(500, TimeUnit.MILLISECONDS) // Search if no char typed for 500ms
                .flatMap(new Function<String, Observable<List<ViewModel>>>() {
                    @Override
                    public Observable<List<ViewModel>> apply(String queryData) throws Exception {
                        if ("".equals(queryData)) {
                            List<ViewModel> searchYtVm = new ArrayList<>();
                            searchYtVm.add(new SearchYtViewModel());
                            return Observable.just(searchYtVm);
                        }
                        return Observable.mergeDelayError(dataService.ytSearch(searchQuery.get())
                                        .map(new Function<YtData.VideoListData, List<ViewModel>>() {
                                            @Override
                                            public List<ViewModel> apply(@NonNull YtData.VideoListData resp) throws Exception {
                                                List<ViewModel> result = new ArrayList<>();
                                                for (YtData.VideoData videoData : resp.getData()) {
                                                    result.add(new YtItemViewModel(videoData, resp.getCached()));
                                                }
                                                return result;
                                            }
                                        })
                                        .delay(500, TimeUnit.MILLISECONDS),
                                getLoadingItems())
                                .onErrorReturn(new Function<Throwable, List<ViewModel>>() {
                                    @Override
                                    public List<ViewModel> apply(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                                        List<ViewModel> errorVm = new ArrayList<>();
                                        errorVm.add(new InternetErrorViewModel());
                                        return errorVm;
                                    }
                                });

                    }
                });

    }

    private Observable<List<ViewModel>> getLoadingItems() {
        List<ViewModel> result = new ArrayList<>();
        result.addAll(Collections.nCopies(3, new LoadingItemViewModel()));
        return Observable.just(result);
    }

}
