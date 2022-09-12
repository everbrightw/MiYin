package com.xiaomi.miyin.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.controllers.UserFlowController;
import com.xiaomi.miyin.model.User;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignupActivity";
    EditText username, password, passwordConfirm;
    MaterialButton signupButton;
    boolean success = true;
    UserFlowController userFlowController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        username = findViewById(R.id.username_input_box);
        password = findViewById(R.id.password_input_box);
        passwordConfirm = findViewById(R.id.password_confirm_box);
        signupButton = findViewById(R.id.signup_btn);
        userFlowController = new UserFlowController(this);
        signupButton.setOnClickListener(this);

        // listening for edit text box input change and update the button color
        initEditTextListeners();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signup_btn){
            User user = new User(username.getText().toString(), password.getText().toString());
            userFlowController.signUp(user);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus() != null){
            UserFlowController.dismissKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void changeButtonColorIfNeeded(Button button,
                                           TextView userText,
                                           TextView passwordText,
                                           TextView confirmPasswordText){
        UserFlowController.changeButtonColorIfNeeded(this, button, userText, passwordText, confirmPasswordText);

    }

    private void initEditTextListeners(){

        // user name text changed listner
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColorIfNeeded(signupButton, username, password, passwordConfirm);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // password text change listener
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColorIfNeeded(signupButton, username, password, passwordConfirm);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColorIfNeeded(signupButton, username, password, passwordConfirm);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
