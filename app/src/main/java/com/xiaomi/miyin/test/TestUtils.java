package com.xiaomi.miyin.test;

import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.xiaomi.miyin.adapters.VideoAdapter;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.model.ResponseStatus;
import com.xiaomi.miyin.model.Video;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Where all the magic happens and my test playground
 */

public class TestUtils {

    public static String BASE_URL = "http://10.235.50.5:50040";
    public static String BASE_URL2 = "https://jsonplaceholder.typicode.com";
    public static String TAG = "TestUtils";

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
     */
    public static void testApi(){

        Call<Test> call = testInterface.getResponse();
        call.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "Failed, Code: " + response.code());
                    return;
                }
                // success!
                Test ret = response.body();
                    String content = "";
                    content += "status_code: " + ret.getStatusCode() + "\n" + " message: " + ret.getMessage() +" \n" + ret.getContent() + " \n";
                    Log.i(TAG, content);
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    // this is where the logged in user's info should look like
    final static String USER_ID = "328694676918341";
    final static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOjMyODY5NDY3NjkxODM0MSwiZXhwIjoxNjYyOTg4OTgzLCJuYmYiOjE2NjIzODM5NDN9.R4DKgXQFqXYX3wGMQFsSAtklZ1h4y6-_ligv6I9rF9I";

    public static void testFetchVideo(ViewPager2 viewPager2){
        IMiVibeApi api = ApiClient.getVideoTestClient().create(IMiVibeApi.class);
        Call<ResponseStatus> call = api.fetchFeedVideos(USER_ID, TOKEN);
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "Failed fetch videos: " + response.code());
                    return;
                }
                //success
                ResponseStatus ret = response.body();
                Log.i(TAG, ret.getStatusMsg() + " ==============SUCCESS===========");
                List<Video> videos = ret.getVideos();
                printVideo(videos);
                viewPager2.setAdapter(new VideoAdapter(videos));
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    public static void printVideo(List<Video> videos){
        Log.i(TAG, "========printing videos ========\n");
        for (int i = 0; i < videos.size(); i++){
            Log.i(TAG, "video: " + i + ": " + videos.get(i).getUrl() + "\n");
            Log.i(TAG, "author: " + "user_id: " + videos.get(i).getUser().getUserId() + "username: " + videos.get(i).getUser().getAccountName());

        }
    }









}
