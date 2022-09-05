package com.xiaomi.miyin.test;

import com.google.gson.annotations.SerializedName;

public class UserTest {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public UserTest(String userName, String password){
        this.username = userName;
        this.password = password;
    }
}
