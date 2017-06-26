package com.softeem.orderapp.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Edward on 2017/6/22.
 */

public class HttpUtils {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final OkHttpClient client = new OkHttpClient();

    // 提交数据:
    //url : 网址
    //json : 需要上传的 json 文本
    //callback : 回调堆笑
    public void postData(String url, String json, final HttpCallback callback) {
        // 创建请求体
        RequestBody body = RequestBody.create(JSON, json);
        // 创建请求对象
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread() {
            public void run() {
                Response response = null;
                try {
                    // 网络请求
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        // 数据提交成功
                        callback.onSuccess(response.body().string());
                    } else {
                        String message = "Unexpected code " + response;

                        callback.onFailure(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getData(String url, final HttpCallback callback) {
        // 创建请求对象
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();

        //开启线程.执行网络操作
        new Thread() {
            public void run() {
                Response response = null;
                try {
                    // 执行网络操作
                    response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        // 获取响应文本
                        String json = response.body().string();
                        callback.onSuccess(json);
                    } else {
                        String message = "Unexpected code " + response;

                        callback.onFailure(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
