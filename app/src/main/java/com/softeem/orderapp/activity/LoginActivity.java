package com.softeem.orderapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softeem.orderapp.R;
import com.softeem.orderapp.utils.Config;
import com.softeem.orderapp.utils.SpUtils;
import com.softeem.orderapp.utils.UserService;
import com.softeem.orderapp.utils.UserServiceImpl;

public class LoginActivity extends Activity {

    TextView username;
    TextView password;
    Button doLogin;
    UserService userService=new UserServiceImpl();
    Handler handler;
    private final int DO_LOGIN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }
    void initData(){
      handler = new Handler(){
          @Override
          public void handleMessage(Message msg) {
              //在此处更改跳转Activity
               Intent t = new Intent(LoginActivity.this,MainActivity.class);
              startActivity(t);
          }
      };
    }
    //提供给外面的Activity，用来尝试自动登陆，失败返回false
    public static Boolean autoLogin(Context ctx){
        String username = SpUtils.read(ctx, "username");
        if(username!=null){
            Long time=SpUtils.getLong(ctx,"time");
            if(time!=0&&System.currentTimeMillis()-time<1000*60*30){
                Config.setUsername(username);
                return true;
            }else{
                SpUtils.write(ctx,"username",null);
                return false;
            }
        }
        return false;
    }
    public static void clearUser(Context ctx){
        Config.setUsername(null);
        SpUtils.write(ctx,"username",null);
    }



    void initView(){
        username=(EditText) findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        doLogin=(Button)findViewById(R.id.button);
        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=username.getText().toString();
                String passWord=password.getText().toString();
                String s = userService.getPassword(userName);
                if(s != null && s.equals(passWord)){
                     Config.setUsername(userName);
                     SpUtils.write(LoginActivity.this,"username",userName);
                     SpUtils.setLong(LoginActivity.this,"time", System.currentTimeMillis());
                     handler.sendEmptyMessage(DO_LOGIN);
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
