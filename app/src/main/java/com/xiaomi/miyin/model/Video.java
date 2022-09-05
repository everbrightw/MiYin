package com.xiaomi.miyin.model;

public class Video {

    private String title;
    private String description;
    private String url;

    public Video(String url, String title, String description){
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}