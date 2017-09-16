package com.upworktest.restcachetest.model

object YtData {
    data class VideoListData(var cached: Boolean, var data: List<VideoData>?)
    data class VideoData(var videoId: String?, var image: String?, var title: String, var publishedDate: String, var description: String?, var channelTitle: String?)
}
