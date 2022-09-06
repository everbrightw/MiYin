package com.xiaomi.miyin.controllers;

import com.xiaomi.miyin.model.User;

//for caching
public final class UserManager {

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


}
