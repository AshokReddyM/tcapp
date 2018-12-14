package com.tollywood24.tollywoodcircle.data.model;

import android.support.annotation.Nullable;

/**
 * Created by trainee on 7/18/2017.
 */

public class VideoItem {

    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String video_id;

    @Nullable
    private String thumbnailURL;

    @Nullable
    private String nextPageToken;

    @Nullable
    private String id;

    @Nullable
    private String channel_name;

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }

}