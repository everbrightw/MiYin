package com.xiaomi.miyin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAvatar {

    @SerializedName("user_image")
    @Expose
    private String userImage;


    public String getUserImage(){
        return userImage;
    }

}
