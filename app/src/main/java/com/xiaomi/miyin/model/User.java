package com.xiaomi.miyin.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("user_id")
    private Integer id;

    private String token;

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("status_msg")
    private String statusMessage;

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public User(String userName, String password){
        this.username = userName;
        this.password = password;
    }
}
