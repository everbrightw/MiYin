package com.xiaomi.miyin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User {

    public static int SIGN_UP_SUCCESS = 0;
    public static int SIGN_UP_EXISTED_USER = 1;
    public static int LOGIN_SUCCESS = 0;
    public static int LOGIN_FAILED = 1;

    String username, password;

    // login & signup response
    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("status_msg")
    private String message;

    @SerializedName("status_code")
    private Integer statusCode;

    @SerializedName("token")
    private String token;


    List<Video> videos = new ArrayList<>();



    public User(String userName, String password){
        this.username = userName;
        this.password = password;
    }

    /**
     * User uploaded a video
     */
    public void addVideo(Video video){
        videos.add(video);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public String getToken() {
        return token;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
