package com.tollywood24.tollywoodcircle.data.model;

import java.io.Serializable;

public class Upload implements Serializable{

    private String unique_key;
    private String title;
    private String imageUrl;
    private String link;
    private String totalViews;
    private String postTime;
    private String description;


    public Upload() {
    }

    public Upload(String title, String imageUrl, String link, String totalViews, String postTime, String unique_key, String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.totalViews = totalViews;
        this.postTime = postTime;
        this.unique_key = unique_key;
        this.description = description;
    }


    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }


    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}