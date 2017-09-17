package com.upworktest.restcachetest.model;


import android.util.Log;

import com.upworktest.restcachetest.TestApplication;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Realm implementation of CacheService
 */

public class RealmCacheService implements CacheService {


    @Override
    public Observable<YtData.VideoListData> getCachedVideos(String searchQuery) {
        Realm realm = Realm.getDefaultInstance(); // different thread so new Instance
        realm.refresh(); // since data is written on the main thread and this is IO thread, to get latest data, need to refresh
        CachedVideoListData data = realm.where(CachedVideoListData.class).equalTo("searchQuery", searchQuery).findFirst();
        if (data == null) {
            return Observable.empty();
        } else {
            List<YtData.VideoData> dataList = new ArrayList<>();
            for (CachedVideoData cachedVideoData : data.getData()) {
                dataList.add(new YtData.VideoData(cachedVideoData.getVideoId(), cachedVideoData.getImage(), cachedVideoData.getTitle(), cachedVideoData.getPublishedDate(), cachedVideoData.getDescription(), cachedVideoData.getChannelTitle()));
            }
            return Observable.just(new YtData.VideoListData(true, dataList));

        }
    }

    @Override
    public YtData.VideoListData putCachedVideos(final String searchQuery, final YtRespModel.SearchResp searchResp) {
        final CachedVideoListData retData = new CachedVideoListData();
        final List<YtData.VideoData> dataList = new ArrayList<>();
        Realm realm = null;
        try {
            // different thread so new Instance
            realm = Realm.getDefaultInstance();
            // realm.executeTransaction takes care of start and commit transaction
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<CachedVideoData> cachedDataList = new RealmList<>();
                    for (YtRespModel.Item item : searchResp.getItems()) {
                        YtRespModel.Snippet snippet = item.getSnippet();
                        cachedDataList.add(CachedVideoData.create(item.getId().getVideoId(), snippet.getThumbnails().getDefault().getUrl(),
                                snippet.getTitle(), snippet.getPublishedAt(), snippet.getDescription(), snippet.getChannelTitle()));
                        dataList.add(new YtData.VideoData(item.getId().getVideoId(), snippet.getThumbnails().getDefault().getUrl(),
                                snippet.getTitle(), snippet.getPublishedAt(), snippet.getDescription(), snippet.getChannelTitle()));
                    }
                    retData.setData(cachedDataList);
                    retData.setSearchQuery(searchQuery);
                    realm.insert(retData);

                }
            });
        } finally {
            if (realm != null)
                realm.close(); // not necessary in main thread, but in case called from other thread its a good practice to avoid leaks
        }
        return new YtData.VideoListData(false, dataList);
    }

    @Override
    public List<String> getSavedQueries() {
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<CachedVideoListData> data = mRealm.where(CachedVideoListData.class).findAll();
        List<String> savedQueries = new ArrayList<>();
        for (CachedVideoListData elem : data) {
            savedQueries.add(elem.getSearchQuery());
        }
        return savedQueries;
    }
}
