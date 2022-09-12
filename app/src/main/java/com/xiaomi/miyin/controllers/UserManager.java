package com.xiaomi.miyin.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xiaomi.miyin.model.User;

//for caching
public final class UserManager {


    public static final String PREF_USER_NAME = "username";
    public static final String PREF_TOKEN = "token";
    private static User loggedInUser;

    public static boolean userIsLoggedIn(){
        return loggedInUser != null;
    }

    public static void setLoggedInUser(User user){
        loggedInUser = user;
    }

    public static String getLoggedInUserName(){
        return loggedInUser.getUsername();
    }

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setToken(Context ctx, String token)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TOKEN, token);
        editor.commit();
    }

    public static String getToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_TOKEN, "");
    }

    public static boolean isLoggedIn(Context ctx){
        return !getUserName(ctx).equals("");
    }

    public static void signOut(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, "");
        editor.putString(PREF_TOKEN, "");
        editor.commit();
    }
}
