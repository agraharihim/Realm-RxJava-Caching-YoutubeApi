/* Cached Data List Wrapper */
package com.upworktest.restcachetest.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class CachedVideoListData extends RealmObject {

    @PrimaryKey
    private String searchQuery;
    private RealmList<CachedVideoData> data;

    public static CachedVideoListData create(String searchQuery, RealmList<CachedVideoData> data) {
        CachedVideoListData listData = new CachedVideoListData();
        listData.setSearchQuery(searchQuery);
        listData.setData(data);
        return listData;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public RealmList<CachedVideoData> getData() {
        return data;
    }

    public void setData(RealmList<CachedVideoData> data) {
        this.data = data;
    }
}
