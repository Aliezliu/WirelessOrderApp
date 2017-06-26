package com.softeem.orderapp;

import android.app.Application;

import com.softeem.orderapp.bean.OrderBean;
import com.softeem.orderapp.bean.TableBean;

/**
 * Created by Edward on 2017/6/22.
 * 自定义一个 Appliction
 */

public class MyApplication extends Application {
    // 创建一个对象: 存储订单信息
    public OrderBean orderBean;


    @Override
    public void onCreate() {
        super.onCreate();
        orderBean = new OrderBean();
    }
}
