package com.tollywood24.tollywoodcircle.data.model;

import java.io.Serializable;

public class Post implements Serializable {

    private String category_id;

    private String description;

    private String imageUrl;

    private String link;

    private String postTime;

    private String title;

    private String totalViews;

    private String unique_key;

    public Post() {
    }

    public Post(String category_id, String description, String imageUrl, String link, String postTime, String title, String totalViews, String unique_key) {
        this.category_id = category_id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.link = link;
        this.postTime = postTime;
        this.title = title;
        this.totalViews = totalViews;
        this.unique_key = unique_key;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }
}
