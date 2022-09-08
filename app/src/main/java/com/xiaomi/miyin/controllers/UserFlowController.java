package com.xiaomi.miyin.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xiaomi.miyin.R;
import com.xiaomi.miyin.activities.MainActivity;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.model.ResponseStatus;
import com.xiaomi.miyin.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFlowController implements View.OnClickListener{


    public static String TAG = "UserFlowController";
    public static int SIGN_UP_BUTTON_ID = R.id.signup_btn;
    public static int LOG_IN_BUTTON_ID = R.id.login_btn;

    private static IMiVibeApi sMiVibeApi = ApiClient.getClient().create(IMiVibeApi.class);

    private User user;
    private Context context;
    private Dialog signUpPage;

    public UserFlowController(Context context, Dialog signUpPage){
        this.context = context;
        this.signUpPage = signUpPage;
    }


    public void setUser(User user){
        this.user = user;
    }

    public void signUp(User user){
        Call<ResponseStatus> call = sMiVibeApi.userRegister(user.getUsername(), user.getPassword());
        call.enqueue(new SignUpResponseCallback(this));
    }

    public void login(User user){
        Log.i(TAG, user.getUsername() + user.getPassword());
        Call<ResponseStatus> call = sMiVibeApi.userLogin(user.getUsername(), user.getPassword());
        call.enqueue(new LoginResponseCallback(this));
    }

    private boolean validFormat(String input){
        if(input == null || input.equals("")){
            return false;
        }
        return true;
    }

    private void closeSignupPage(){
        signUpPage.dismiss();
    }

    private Context getContext(){
        return context;
    }

    private void closeLoginPage() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        //switch (v.getId()){
        //    case SIGN_UP_BUTTON_ID:
        //        Log.i(TAG, "sign up button clicked, check ");
        //        signUp(user);
        //    case LOG_IN_BUTTON_ID:
        //        Log.i(TAG, "login button clicked,");
        //
        //        break;
        //    default:
        //        throw new IllegalStateException("Unexpected value: " + v.getId());
        //}
    }

    public static class SignUpResponseCallback implements Callback<ResponseStatus>{

        UserFlowController userFlowController;
        Context context;

        SignUpResponseCallback(UserFlowController userFlowController){
            this.userFlowController = userFlowController;
            this.context = userFlowController.getContext();

        }

        @Override
        public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
            if(!response.isSuccessful()){
                Log.i(TAG, "fail register user");
                return;
            }

            ResponseStatus responseStatus = response.body();
            Toast.makeText(context, "user has been registered" + responseStatus.getStatusCode()
                    + " user id: " + responseStatus.getUserId(), Toast.LENGTH_SHORT).show();
            if(responseStatus.getStatusCode() == 0){
                // successfully signed up
                // close the sign up page
                userFlowController.closeSignupPage();
            }
            if(responseStatus.getStatusCode() == 1){
                // user has been registered before
                Toast.makeText(context, "User has been registered before", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<ResponseStatus> call, Throwable t) {
            Toast.makeText(context, "Error connecting to the server", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "error: " + t.getMessage());
        }
    }

    public static class LoginResponseCallback implements Callback<ResponseStatus> {
        UserFlowController userFlowController;
        Context context;

        LoginResponseCallback(UserFlowController userFlowController){
            this.userFlowController = userFlowController;
            this.context = userFlowController.getContext();

        }

        @Override
        public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
            if(!response.isSuccessful()){
                Log.i(TAG, "fail register user");
                return;
            }

            ResponseStatus responseStatus = response.body();
            if(responseStatus.getStatusCode() == 0){
                // successfully logged in
                // close the login page
                userFlowController.closeLoginPage();
                UserManager.setLoggedInUser(userFlowController.user);
            }
            if(responseStatus.getStatusCode() == 1){
                // username or password error
                Toast.makeText(context, "Username does not exist or password is wrong", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<ResponseStatus> call, Throwable t) {
            Toast.makeText(context, "Error connecting to the server", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "error: " + t.getMessage());
        }
    }



}
