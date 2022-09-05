package com.xiaomi.miyin.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.model.User;
import com.xiaomi.miyin.test.UserTest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    TextView username;
    TextView password;
    TextView signup;
    MaterialButton button;
    boolean success = true;

    private static IMiVibeApi sMiVibeApi = ApiClient.getClient().create(IMiVibeApi.class);

    // signup dialog
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        username = findViewById(R.id.username_input_box);
        password = findViewById(R.id.password_input_box);
        button = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_text);

        dialog = new Dialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "login button clicked, check credentials here");
                if(success){
                    // user logged in
                    openMainPage();
                    //for testing
                    //signup("wangyusen111", "wangyusen11");
                }
            }
        });

        // sign up
        // TODO: refactor this
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "sign up clicked, show signup page");
                showSignUp();
            }
        });

    }


    private void signup(String username, String password){
        final UserTest userTest = new UserTest(username, password);

        Call<User> call = sMiVibeApi.userRegister(userTest.getUsername(), userTest.getPassword());
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
                content += "status_msg: " + userRegisterResponse.getMessage() + "\n";
                content += "user_id: " + userRegisterResponse.getUserId() + "\n";
                Log.i(TAG, "\n ===== register response =====: \n" + content);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus() != null){
            dismissKeyboard();
        }
        return super.dispatchTouchEvent(ev);
    }

    void dismissKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    void openMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void showSignUp(){
        dialog.setContentView(R.layout.popup_singup);

        EditText passwordEditText = dialog.findViewById(R.id.password_signup_input_box);
        EditText userNameEditText = dialog.findViewById(R.id.username_signup_input_box);
        TextView close = dialog.findViewById(R.id.popup_close_btn);
        MaterialButton signupButton = dialog.findViewById(R.id.signup_btn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: this is where we have to send the signup request
                signup("yusenw2@illinois.edu", "123456");

            }
        });
        dialog.show();
    }

}
