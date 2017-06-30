package com.softeem.orderapp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.lang.reflect.Type;

/**
 * Created by 王致尧 on 2017/6/29.
 */

public class UserServiceImpl implements UserService{

    private  String password = null;
    @Override
    public String getPassword(String username) {

        new HttpUtils().getData(ServerUrl.GET_PASSWORD_BY_USERNAME + username,getPasswordCallback);
        return password;
    }
    private HttpCallback getPasswordCallback = new HttpCallback() {
        @Override
        public void onSuccess(Object data) {
            Type t = new TypeToken<String>(){}.getType();
            password = new Gson().fromJson(data.toString(),t);
            Log.d("password",password);
        }

        @Override
        public void onFailure(String message) {
            password = null;
        }
    };
}
