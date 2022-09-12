package com.xiaomi.miyin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseStatus {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    @SerializedName("status_msg")
    @Expose
    private String statusMsg;

    @SerializedName("video_list")
    @Expose
    private List<Video> videos;

    @SerializedName("user_id")
    @Expose
    private long userId;

    @SerializedName("token")
    private String token;

    @SerializedName("User")
    @Expose
    private UserAvatar userAvatar;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public long getUserId() {
        return userId;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public String getToken(){
        return token;
    }

    public UserAvatar getUser(){
        return userAvatar;
    }

}
