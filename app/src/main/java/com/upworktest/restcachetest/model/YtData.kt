package com.upworktest.restcachetest.model

/**
 * Contains data class which is used to display results. both YtRespModel and CachedVideoListdata are mapped to this data class
 */
object YtData {
    data class VideoListData(var cached: Boolean, var data: List<VideoData>?)
    data class VideoData(var videoId: String?, var image: String?, var title: String, var publishedDate: String, var description: String?, var channelTitle: String?)
}
