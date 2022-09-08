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


    /**
     * Test ping
     * @return
     */
    @GET("/test")
    Call<Test> getResponse();

    /**
     * User register
     * @param username
     * @param password
     * @return
     */

    @POST("miyin/user/register/")
    Call<ResponseStatus> userRegister(
            @Query("username") String username, 
            @Query("password") String password
    );


    /**
     * User log in
     * @param username
     * @param password
     * @return
     */
    @POST("miyin/user/login/")
    Call<ResponseStatus> userLogin(
            @Query("username") String username, 
            @Query("password") String password
    );


    /**
     * Fetch all uploaded videos from the server
     * @param userId
     * @param token
     * @return
     */
    @GET("miyin/feed/")
    Call<ResponseStatus> fetchFeedVideos(
        @Query("user_id") String userId,
        @Query("token") String token
    );

    /**
     * Publish a user selected video
     * @param token
     * @param video
     * @return
     */
    @Multipart
    @POST("miyin/publish/action/")
    Call<ResponseBody> uploadVideo(
            @Query("token") String token,
            @Part() MultipartBody.Part video
    );

}
