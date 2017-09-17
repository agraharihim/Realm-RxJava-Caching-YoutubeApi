package com.upworktest.restcachetest.model;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Contains logic for  merging cached and network responsese
 */

public class DataflowService {

    private ApiService mApi;
    private CacheService mCache;

    public DataflowService(ApiService api) {
        this.mApi = api;
        mCache = new RealmCacheService();
    }

    public String getSavedQueries() {
        return TextUtils.join(" , ", mCache.getSavedQueries());
    }

    public Observable<YtData.VideoListData> ytSearch(final String searchKeyword) {

        return mCache.getCachedVideos(searchKeyword)
                .defaultIfEmpty(new YtData.VideoListData(false, null))
                .flatMap(new Function<YtData.VideoListData, ObservableSource<YtData.VideoListData>>() {
                    @Override
                    public ObservableSource<YtData.VideoListData> apply(YtData.VideoListData data) throws Exception {
                        if (data.getData() == null) { // fetch from Api if not cached
                            return mApi.ytSearch("snippet", "10", "", searchKeyword, "", "AIzaSyBfvv-SnXCD60eTAqG1AGQLQ-_upR9gfKQ")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(new Function<YtRespModel.SearchResp, YtData.VideoListData>() {
                                        @Override
                                        public YtData.VideoListData apply(@NonNull YtRespModel.SearchResp ytResp) throws Exception {
                                            return mCache.putCachedVideos(searchKeyword, ytResp);
                                        }
                                    });
                        } else {
                            Log.d("CACHE", "returning from cache");
                            return mCache.getCachedVideos(searchKeyword);
                        }
                    }
                });
    }

}
