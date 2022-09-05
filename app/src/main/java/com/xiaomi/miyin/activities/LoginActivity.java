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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    TextView username;
    TextView password;
    TextView signup;
    MaterialButton button;
    boolean success = true;

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
                // TODO: this is where we have to send the rest client request
            }
        });
        dialog.show();
    }

}
