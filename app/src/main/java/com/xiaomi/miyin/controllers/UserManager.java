package com.xiaomi.miyin.controllers;

import com.xiaomi.miyin.model.User;

public interface UserManager {

    User signUp();
    User login();
    User signOut();


}
