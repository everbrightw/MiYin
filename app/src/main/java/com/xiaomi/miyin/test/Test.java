package com.xiaomi.miyin.test;

import com.google.gson.annotations.SerializedName;

public class Test {

    private int statusCode;

    @SerializedName("Msg")
    private String message;

    @SerializedName("info")
    private String content;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }
}
