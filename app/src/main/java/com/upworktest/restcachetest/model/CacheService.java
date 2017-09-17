package com.upworktest.restcachetest.model;

import java.util.List;

import io.reactivex.Observable;

/**
 * declares methods which interact with cache
 */

public interface CacheService {

    Observable<YtData.VideoListData> getCachedVideos(String searchQuery);

    YtData.VideoListData putCachedVideos(String searchQuery, YtRespModel.SearchResp searchResp);

    List<String> getSavedQueries();

}
