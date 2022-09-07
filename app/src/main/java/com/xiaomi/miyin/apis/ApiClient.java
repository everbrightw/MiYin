package com.xiaomi.miyin.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://10.235.50.5:50040/";
    public static final String VIDEO_URL = "http://10.235.120.209:7310/";
    private static Retrofit retrofit = null;
    private static Retrofit videoRetrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getVideoTestClient(){
        if(videoRetrofit == null){
            videoRetrofit = new Retrofit.Builder()
                    .baseUrl(VIDEO_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return videoRetrofit;
    }

}