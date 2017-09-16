package com.upworktest.restcachetest.viewmodel;

import com.upworktest.restcachetest.model.YtData;

public class YtItemViewModel extends ViewModel {

    public final String image;
    public final String title;
    public final String publishedDate;
    public final String description;
    public final String channelTitle;
    public final String videoId;
    public final boolean cached;

    public YtItemViewModel(YtData.VideoData videoData, boolean cached) {
        this.image = videoData.getImage();
        this.title = videoData.getTitle();
        this.publishedDate = videoData.getPublishedDate();
        this.description = videoData.getDescription();
        this.channelTitle = videoData.getChannelTitle();
        this.videoId = videoData.getVideoId();
        this.cached = cached;
    }


}
