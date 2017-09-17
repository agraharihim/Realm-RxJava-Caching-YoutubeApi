package com.upworktest.restcachetest.model;

import io.realm.RealmObject;

/**
 * Cached Individual Video Data
 */

public class CachedVideoData extends RealmObject {

    private String videoId;
    private String image;
    private String title;
    private String publishedDate;
    private String description;
    private String channelTitle;

    public static CachedVideoData create(String videoId, String image, String title, String publishedDate, String description, String channelTitle) {
        CachedVideoData cachedVideoData = new CachedVideoData();
        cachedVideoData.setVideoId(videoId);
        cachedVideoData.setImage(image);
        cachedVideoData.setTitle(title);
        cachedVideoData.setPublishedDate(publishedDate);
        cachedVideoData.setDescription(description);
        cachedVideoData.setChannelTitle(channelTitle);
        return cachedVideoData;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
