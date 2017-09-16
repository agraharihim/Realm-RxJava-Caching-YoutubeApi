package com.upworktest.restcachetest.model;


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    fun ytSearch(@Query("part") part: String, @Query("maxResults") maxResults: String,
                 @Query("pageToken") pageToken: String, @Query("q") query: String,
                 @Query("type") type: String, @Query("key") apiKey: String): Observable<YtRespModel.SearchResp>
}
