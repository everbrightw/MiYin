package com.xiaomi.miyin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseStatus {

    @SerializedName("status_code")
    private Integer statusCode;

    @SerializedName("status_msg")
    private String statusMsg;

    @SerializedName("video_list")
    @Expose
    private List<Video> videos;

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public Integer getUserId() {
        return userId;
    }

    public List<Video> getVideos() {
        return videos;
    }

}
