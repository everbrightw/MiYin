package com.xiaomi.miyin.model;

import com.google.gson.annotations.SerializedName;

public class Credential {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    // response from sign up
    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("status_msg")
    private String message;

    @SerializedName("status_code")
    private Integer statusCode;

    public Credential(String userName, String password){
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode(){
        return statusCode;
    }


}
