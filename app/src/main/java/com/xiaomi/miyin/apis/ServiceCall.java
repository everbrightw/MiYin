package com.xiaomi.miyin.apis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Picasso;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.adapters.ProfileGridViewAdapter;
import com.xiaomi.miyin.adapters.VideoAdapter;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.controllers.UserManager;
import com.xiaomi.miyin.fragments.OfflineFragment;
import com.xiaomi.miyin.model.LoadingView;
import com.xiaomi.miyin.model.ResponseStatus;
import com.xiaomi.miyin.model.Video;
import com.xiaomi.miyin.test.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Where all the magic happens and my test playground
 */

public class ServiceCall {

    public static String BASE_URL = "http://10.235.50.5:50040";
    public static String BASE_URL2 = "https://jsonplaceholder.typicode.com";
    public static String TAG = "ServiceCall";

    public static Retrofit retrofit;
    public static IMiVibeApi testInterface;

    public static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    public static OkHttpClient client;

    static {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        testInterface = retrofit.create(IMiVibeApi.class);
    }

    /**
     * Test basic connection
     * @param fragmentManager
     */
    public static void testApi(FragmentManager fragmentManager) {

        Call<Test> call = testInterface.getResponse();
        call.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Failed, Code: " + response.code());
                    return;
                }
                // success!
                Test ret = response.body();
                String content = "";
                content += "status_code: " + ret.getStatusCode() + "\n" + " message: " + ret.getMessage() + " \n" + ret.getContent() + " \n";
                Log.i(TAG, content);
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new OfflineFragment()).commit();
            }
        });
    }

    // this is where the logged in user's info should look like
    final static String USER_ID = "328694676918341";
    //final public static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOjMyODY5NDY3NjkxODM0MSwiZXhwIjoxNjYyOTg4OTgzLCJuYmYiOjE2NjIzODM5NDN9.R4DKgXQFqXYX3wGMQFsSAtklZ1h4y6-_ligv6I9rF9I";
    final public static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOjMzMDY4NjM4Nzk0MTQ0NSwiZXhwIjoxNjYzNDc1MTgzLCJuYmYiOjE2NjI4NzAxNDN9.gTDRtSjf_jFAae-V1evMOREV73FTw-5wD8Vz6qn9690";

    // like a video constants
    public final static String LIKE = "1";
    public final static String UNLIKE = "2";


    public static void retrieveFeedVideos(FragmentManager fragmentManager, ViewPager2 viewPager2,
                                          ProgressBar progressBar, LoadingView loadingView) {
        IMiVibeApi api = ApiClient.getVideoTestClient().create(IMiVibeApi.class);
        Call<ResponseStatus> call = api.fetchFeedVideos(UserManager.getToken(viewPager2.getContext()));
        Log.i(TAG, "token" + UserManager.getToken(viewPager2.getContext()));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Failed fetch videos: " + response.code());
                    return;
                }
                //success
                progressBar.setVisibility(View.GONE);
                ResponseStatus ret = response.body();
                Log.i(TAG, ret.getStatusMsg() + " ==============SUCCESS===========");
                List<Video> videos = ret.getVideos();
                printVideo(videos);
                VideoAdapter videoAdapter = new VideoAdapter(videos);
                viewPager2.setAdapter(videoAdapter);

            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new OfflineFragment());
                loadingView.showOfflineInfo();
            }
        });
    }

    public static void printVideo(List<Video> videos) {
        Log.i(TAG, "========printing videos ========\n");
        if(videos == null){
            return;
        }
        for (int i = 0; i < videos.size(); i++) {
            Video video = videos.get(i);
            Log.i(TAG, "video: " + i + ": " + video.getUrl() + "\n");
            //Log.i(TAG, "author: " + "user_id: " + videos.get(i).getUser().getUserId() + "username: " + videos.get(i).getUser().getAccountName());
            Log.i(TAG, "isfavorite; " + video.isFavorite());

        }
    }

    public static List<Video> generateFakeVideos(){
        List<Video> videos = new ArrayList<>();
        Video video1 = new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                "@usertest1", "okokokok");
        videos.add(video1);

        Video video2 = new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                "@usertest2", "okokokokuiucthebestuiucthebestuiucthbestuiuctebtestuiucubtest");
        videos.add(video2);
        return videos;
    }

    public static void retrieveUserPublishedVideos(Context context, RecyclerView recyclerView){
        IMiVibeApi api = ApiClient.getVideoTestClient().create(IMiVibeApi.class);
        Call<ResponseStatus> call = api.getPublishedVideos(UserManager.getToken(context));
        Log.i(TAG, "token" + UserManager.getToken(context));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Failed fetch videos: " + response.code());
                    return;
                }
                //success
                ResponseStatus ret = response.body();
                Log.i(TAG, ret.getStatusMsg() + " ==============SUCCESS===========");
                List<com.xiaomi.miyin.model.Video> videos = ret.getVideos();
                printVideo(videos);
                List<String> titles = new ArrayList<>();
                List<String> images = new ArrayList<>();
                if(videos==null){
                    return;
                }
                for(Video video : videos){
                    titles.add(""+video.getFavCount());
                    //images.add(getBitmapFromURL(video.getCoverUrl()));
                    images.add(video.getCoverUrl());
                }
                ProfileGridViewAdapter profileGridViewAdapter  = new ProfileGridViewAdapter(context, titles, images);
                recyclerView.setAdapter(profileGridViewAdapter);

            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    public static void retrieveUserLikedVideos(Context context, RecyclerView recyclerView){
        IMiVibeApi api = ApiClient.getVideoTestClient().create(IMiVibeApi.class);
        Call<ResponseStatus> call = api.getLikedVideos(UserManager.getToken(context));
        Log.i(TAG, "token" + UserManager.getToken(context));
        Log.i(TAG, "user name" + UserManager.getUserName(context));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Failed fetch videos: " + response.code());
                    return;
                }
                //success
                ResponseStatus ret = response.body();
                Log.i(TAG, ret.getStatusMsg() + " ==============SUCCESS===========");
                List<com.xiaomi.miyin.model.Video> videos = ret.getVideos();
                printVideo(videos);
                List<String> titles = new ArrayList<>();
                List<String> images = new ArrayList<>();
                if(videos == null){
                    ProfileGridViewAdapter profileGridViewAdapter  = new ProfileGridViewAdapter(context, titles, images);
                    recyclerView.setAdapter(profileGridViewAdapter);
                    return;
                }
                for(Video video : videos){
                    titles.add(""+video.getFavCount());
                    //images.add(getBitmapFromURL(video.getCoverUrl()));
                    images.add(video.getCoverUrl());
                }
                ProfileGridViewAdapter profileGridViewAdapter  = new ProfileGridViewAdapter(context, titles, images);
                recyclerView.setAdapter(profileGridViewAdapter);

            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });

    }

    public static void testUserImage(Context context, ImageView avatar){
        IMiVibeApi api = ApiClient.getVideoTestClient().create(IMiVibeApi.class);
        Call<ResponseStatus> call = api.getUserInfo(UserManager.getToken(context));
        Log.i(TAG, "token" + UserManager.getToken(context));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Failed fetch videos: " + response.code());
                    return;
                }
                //success
                ResponseStatus ret = response.body();
                Picasso.get().load(ret.getUser().getUserImage()).into(avatar);
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

}
