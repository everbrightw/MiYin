package com.xiaomi.miyin.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.controllers.UserFlowController;
import com.xiaomi.miyin.controllers.UserManager;
import com.xiaomi.miyin.model.User;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    TextView username;
    TextView password;
    TextView signup;
    MaterialButton loginButton;
    boolean success = true;
    UserFlowController userFlowController;

    // signup dialog
    Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.R)
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
        loginButton = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_text);

        dialog = new Dialog(this);
        userFlowController = new UserFlowController(this, dialog);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "login button clicked, check credentials here");
                if(success){
                    // user logged in
                    userFlowController.login(new User(username.getText().toString(), password.getText().toString()));
                    Log.i(TAG, "username: " + username.getText().toString());
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
                //showSignUp();
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
                //TestUtils.testFetchVideo();
            }
        });

        // TODO: refactor this
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // if the user fill in both of the input text, change the button color
                changeButtonColorIfNeeded(loginButton, username, password);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // TODO: refactor this
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColorIfNeeded(loginButton, username, password);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus() != null){
            UserFlowController.dismissKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    void openMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    EditText passwordEditText, userNameEditText;

    void showSignUp(){
        dialog.setContentView(R.layout.popup_singup);

        passwordEditText = dialog.findViewById(R.id.password_signup_input_box);
        userNameEditText = dialog.findViewById(R.id.username_signup_input_box);
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
                User user = new User(userNameEditText.getText().toString(), passwordEditText.getText().toString());
                userFlowController.setUser(user);
                userFlowController.signUp(user);
            }
        });

        // TODO: refactor this
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColorIfNeeded(signupButton, userNameEditText, passwordEditText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // TODO: refactor this
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColorIfNeeded(signupButton, userNameEditText, passwordEditText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    private void changeButtonColorIfNeeded(Button button, TextView userText, TextView passwordText){
        UserFlowController.changeButtonColorIfNeeded(this, button ,userText, passwordText);
    }

}
