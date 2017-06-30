package com.softeem.orderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeem.orderapp.R;
import com.softeem.orderapp.bean.OrderBean;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.lang.reflect.Type;

public class PayActivity extends AppCompatActivity {

    private EditText orderInput;//订单编号输入框
    private Button confirm;
    private ImageView code;
    private Button cancel;
    private Button viewDetail;
    private OrderBean orderBean;
    private String orderInputStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        orderInput = (EditText)findViewById(R.id.orderInput);
        confirm = (Button)findViewById(R.id.confirm);
        code = (ImageView)findViewById(R.id.code);
        cancel = (Button)findViewById(R.id.cancel);
        viewDetail = (Button)findViewById(R.id.viewDetail);

        confirm.setOnClickListener(orderInputListener);
        cancel.setOnClickListener(cancelListener);
    }

    View.OnClickListener viewDetailListener = new View.OnClickListener(){
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PayActivity.this,ViewOrderDetailActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener cancelListener = new View.OnClickListener(){
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PayActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener orderInputListener = new View.OnClickListener(){
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            orderInputStr = orderInput.getText().toString();
            new HttpUtils().getData(ServerUrl.GET_Check_BY_OrderID + orderInputStr,orderInputListenerCallback);

        }
    };

    HttpCallback orderInputListenerCallback = new HttpCallback() {
        @Override
        public void onSuccess(Object data) {
            Type t = new TypeToken<OrderBean>() {}.getType();
            Log.d("data",data.toString());
            orderBean = new Gson().fromJson(data.toString(),t);
            if(orderBean==null)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        code.setVisibility(View.INVISIBLE);
                        viewDetail.setVisibility(View.INVISIBLE);
                        Log.d("Tips","没有该订单的信息");
                        Toast.makeText(PayActivity.this,"没有该订单的信息",Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        code.setVisibility(View.VISIBLE);
                       // viewDetail.setVisibility(View.VISIBLE);
                        Log.d("Tips","请扫描二维码支付");
                        Toast.makeText(PayActivity.this,"请扫描二维码支付",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
;
        @Override
        public void onFailure(String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    code.setVisibility(View.INVISIBLE);
                    viewDetail.setVisibility(View.INVISIBLE);
                    Log.d("Tips","请检查网络是否异常");
                    Toast.makeText(PayActivity.this,"请检查网络是否异常",Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
