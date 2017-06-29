package com.softeem.orderapp.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeem.orderapp.bean.MenuBean;
import com.softeem.orderapp.bean.TypeBean;
import com.softeem.orderapp.constant.ServerUrl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Edward on 2017/6/20.
 */

public class MenuHttpUtils {
    // 做网络请求使用的对象
    private static final OkHttpClient client = new OkHttpClient();

    public void getAllTypes(final HttpCallback callback){

        Type t =  new TypeToken<List<TypeBean>>(){}.getType();
        getData(ServerUrl.GET_ALL_TYPE,callback,t);
    }


    public void getAllMenu(final HttpCallback callback){
        Type t = new TypeToken<List<MenuBean>>(){}.getType();
        getData(ServerUrl.GET_ALL_MENU,callback,t);
    }

    public void getMenuByType(int typeId,final HttpCallback callback){
        Type t = new TypeToken<List<MenuBean>>(){}.getType();
        getData(ServerUrl.GET_MENU_BY_TYPE + typeId,callback,t);
    }

    public void getTypeName(int menuId,final HttpCallback callback){
        Type t = new TypeToken<TypeBean>(){}.getType();
        getData(ServerUrl.GET_TYPE_BY_MENU_ID+menuId,callback,t);
    }


    public void getData(String url,final HttpCallback callback,final Type type){
        // 创建请求对象
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();

        //开启线程.执行网络操作
        new Thread(){
            public void run() {
                Response response = null;
                try {
                    // 执行网络操作
                    response = client.newCall(request).execute();


                    if (response.isSuccessful()) {
                        // 获取响应文本
                        String json = response.body().string();

                        Log.i("OkHttp","打印GET响应的数据：" + json);

                        //json --> java对象
                        Gson gson = new Gson();
                        Object data = gson.fromJson(json,type);

                        // json 处理工具
                        callback.onSuccess(data);
                    } else {
                        String message ="Unexpected code " + response;
                        callback.onFailure(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
