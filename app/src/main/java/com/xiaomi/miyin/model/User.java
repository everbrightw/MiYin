package com.xiaomi.miyin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User {

    public static int SIGN_UP_SUCCESS = 0;
    public static int SIGN_UP_EXISTED_USER = 1;
    public static int LOGIN_SUCCESS = 0;
    public static int LOGIN_FAILED = 1;

    String username, password;


    @SerializedName("follow_count")
    @Expose
    private Integer followCount;

    private Integer followerCount;

    @SerializedName("username")
    @Expose
    private String accountName;

    @SerializedName("user_image")
    @Expose
    private String userAvatar;

    public String getUserAvatar(){
        return userAvatar;
    }

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


    public Integer getFollowCount() {
        return followCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public String getAccountName() {
        return accountName;
    }
}
