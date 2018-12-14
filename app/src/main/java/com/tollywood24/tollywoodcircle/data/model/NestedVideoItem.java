package com.tollywood24.tollywoodcircle.data.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by trainee on 7/18/2017.
 */

public class NestedVideoItem {

    @Nullable
    private String title;

    @Nullable
    private List<VideoItem> videoItems;

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public List<VideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(@Nullable List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }
}