package com.upworktest.restcachetest.model;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by agrahari on 15/09/17.
 */

public interface CacheService {

    Observable<YtData.VideoListData> getCachedVideos(String searchQuery);

    YtData.VideoListData putCachedVideos(String searchQuery, YtRespModel.SearchResp searchResp);

    List<String> getSavedQueries();

}
