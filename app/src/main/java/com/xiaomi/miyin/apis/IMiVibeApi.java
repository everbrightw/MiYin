package com.xiaomi.miyin.apis;

import com.xiaomi.miyin.model.ResponseStatus;
import com.xiaomi.miyin.test.Test;
import com.xiaomi.miyin.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("miyin/publish/action/")
    Call<ResponseBody> uploadVideo(
            @Query("token") String token,
            @Part() MultipartBody.Part video
    );

}
