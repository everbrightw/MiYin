package com.xiaomi.miyin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("favorite_count")
    @Expose
    private Integer favCount = 0;

    private String title;

    private String description;

    @SerializedName("play_url")
    @Expose
    private String url;

    private int length; // in seconds ?

    @SerializedName("author")
    @Expose
    private User user;

    @SerializedName("cover_url")
    private String coverUrl;

    public Video(String url, String title, String description){
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public Video(){
        this.title = "testtest";
        this.description = "okokok";
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

    public int getLength(){
        return length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public long getId() {
        return id;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public User getUser(){
        return user;
    }

    public String getCoverUrl(){
        return coverUrl;
    }

}