package com.king.zxing.app;

import com.google.firebase.Firebase;

public class User {
    public String login,password,role;

    public User() {
    }

    public User(String login, String password,String role) {
        this.login = login;
        this.password = password;
        this.role=role;
    }
    public boolean matching(User one,User two){
        if(one.login.equals(two.login)&&one.password.equals(two.password)){
            return true;
        }
        else {
            return false;
        }
    }

}
