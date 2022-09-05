package com.xiaomi.miyin.apis;

import com.xiaomi.miyin.test.Test;
import com.xiaomi.miyin.model.User;
import com.xiaomi.miyin.test.UserTest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMiVibeApi {

    @GET("/test")
    Call<Test> getResponse();

    @POST("miyin/user/register/")
    Call<User> userRegister(
            @Query("username") String username, 
            @Query("password") String password
    );

    @POST("miyin/user/login/")
    Call<User> userLogin(
            @Query("username") String username, 
            @Query("password") String password
    );
    
}
