package com.softeem.orderapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 王致尧 on 2017/6/29.
 */

public class SpUtils {
    private static SharedPreferences sp=null;
    public static void write(Context ctx ,String key,String value){
        if(sp==null){
            sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        sp.edit().putString(key,value).commit();
    }
    public static void setLong(Context ctx ,String key,Long value){
        if(sp==null){
            sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        sp.edit().putLong(key,value).commit();
    }
    public static Long getLong(Context ctx,String key ){
        if(sp==null){
            sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

       return  sp.getLong(key,0);
    }
    public static String read(Context ctx,String key){
        if(sp==null){
            sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        String userName=sp.getString("username",null);
        return userName;
    }
}
