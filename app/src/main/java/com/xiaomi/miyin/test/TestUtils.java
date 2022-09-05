package com.xiaomi.miyin.test;

import android.util.Log;

import com.xiaomi.miyin.apis.TestInterface;
import com.xiaomi.miyin.model.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestUtils {

    public static String BASE_URL = "http://10.235.50.5:50040";
    public static String BASE_URL2 = "https://jsonplaceholder.typicode.com";
    public static String TAG = "TestUtils";

    public static Retrofit retrofit;
    public static TestInterface testInterface;

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

        testInterface = retrofit.create(TestInterface.class);
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

    /**
     * Test for registering user
     */
    public static void testRegister(){
        UserTest userTest = new UserTest("test2323@xiaomi.com", "12324526");
        Call<User> call = testInterface.userRegister(userTest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "fail register user");
                    Log.i(TAG, "code: " + response.code() + "\n message:" + response.message() + " red: " + response.headers().get("location"));
                    return;
                }

                User userRegisterResponse = response.body();
                String content = "";
                content += "status_code: " + userRegisterResponse.getStatusCode() + "\n";
                content += "status_msg: " + userRegisterResponse.getStatusMessage() + "\n";
                content += "token: " + userRegisterResponse.getToken() + "\n";
                content += "userid: " + userRegisterResponse.getId() + "\n";
                Log.i(TAG, "\n ===== register response =====: \n" + content);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });

    }

}
