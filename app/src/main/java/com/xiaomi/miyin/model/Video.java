package com.xiaomi.miyin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kotlin.ExperimentalStdlibApi;

public class Video {

    @SerializedName("video_id")
    @Expose
    private long videoID;

    @SerializedName("favorite_count")
    @Expose
    private Integer favCount = 0;

    @SerializedName("author_name")
    private String title;

    private String description;

    @SerializedName("play_url")
    @Expose
    private String url;

    // wheather the current video should be displayed as favorite based on the current user
    @SerializedName("is_favorite")
    @Expose
    private boolean isFavorite;

    private int length; // in seconds ?

    //@SerializedName("author")
    //@Expose
    //private User user;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("image_url")
    private String avatarUrl;

    public Video(String url, String title, String description){
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public Video(){
        this.description = "这是一个视频描述";
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

    public Integer getFavCount() {
        return favCount;
    }

    //public User getUser(){
    //    return user;
    //}

    public String getCoverUrl(){
        return coverUrl;
    }

    public long getVideoID(){
        return videoID;
    }

    public boolean isFavorite(){
        return isFavorite;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }

}