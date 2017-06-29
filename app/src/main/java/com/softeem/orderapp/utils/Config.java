package com.softeem.orderapp.utils;

/**
 * Created by 王致尧 on 2017/6/29.
 */

public class Config {
    private static String username=null;
    public static void setUsername(String u){
        username=u;
    }
    public static String getUsername() {
        return username;
    }
}
