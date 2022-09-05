package com.xiaomi.miyin.test;

import com.google.gson.annotations.SerializedName;

public class UserTest {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;


    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("status_msg")
    private String message;

    @SerializedName("status_code")
    private Integer statusCode;

    public UserTest(String userName, String password){
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
