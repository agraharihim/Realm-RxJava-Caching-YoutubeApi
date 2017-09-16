package com.upworktest.restcachetest.model

object YtRespModel {

    data class SearchResp(var kind: String?, var etag: String?, var nextPageToken: String?, var prevPageToken: String?
                          , var pageInfo: PageInfo?, var items: List<Item>)
    data class Image(var url: String?, var width: Int, var height: Int)
    data class Thumbnails(var default: Image?, var medium: Image?, var high: Image?)
    data class Id(var kind: String, var videoId: String)
    data class Item(var kind: String?, var etag: String?, var id: Id?, var snippet: Snippet)
    data class PageInfo(var totalResults: Int, var resultsPerPage: Int)
    data class Snippet(var publishedAt: String?, var channelId: String?, var title: String?, var description: String?
                       , var thumbnails: Thumbnails, var channelTitle: String?, var liveBroadcastContent: String?)
}
