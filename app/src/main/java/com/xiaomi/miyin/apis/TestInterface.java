package com.xiaomi.miyin.apis;

import com.xiaomi.miyin.test.Test;
import com.xiaomi.miyin.model.User;
import com.xiaomi.miyin.test.UserTest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestInterface {

    @GET("test")
    Call<Test> getResponse();

    @POST("miyin/user/register/")
    Call<User> userRegister(@Body UserTest userTest);
}
