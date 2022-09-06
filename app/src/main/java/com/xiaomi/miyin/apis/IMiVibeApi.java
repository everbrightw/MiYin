package com.xiaomi.miyin.apis;

import com.xiaomi.miyin.model.ResponseStatus;
import com.xiaomi.miyin.test.Test;
import com.xiaomi.miyin.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMiVibeApi {

    @GET("/test")
    Call<Test> getResponse();

    @POST("miyin/user/register/")
    Call<ResponseStatus> userRegister(
            @Query("username") String username, 
            @Query("password") String password
    );

    @POST("miyin/user/login/")
    Call<ResponseStatus> userLogin(
            @Query("username") String username, 
            @Query("password") String password
    );

    @GET("miyin/feed/")
    Call<ResponseStatus> fetchFeedVideos(
        @Query("user_id") String userId,
        @Query("token") String token
    );

}
