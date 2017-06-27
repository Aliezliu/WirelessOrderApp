package com.softeem.orderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softeem.orderapp.MyApplication;
import com.softeem.orderapp.R;
import com.softeem.orderapp.adapter.OrderItemListAdapter;
import com.softeem.orderapp.bean.OrderBean;
import com.softeem.orderapp.bean.OrderBeanForJson;
import com.softeem.orderapp.bean.OrderItemBean;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.text.DecimalFormat;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView orderListView;
    private TextView totalPriceTextView;
    private TextView cutPriceTextView;
    private TextView realPriceTextView;
    private Button putOrderButton;
    private Button addOrderButton;
    private Button payOrderButton;
    private Button orderBackButton;

    private OrderBean orderBean;

    private OrderItemListAdapter adapter;
    List<OrderItemBean> orderItemBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //初始化控件
        initView();

        //初始化数据
        initData();
    }

    public void initView() {
        orderListView = (ListView) findViewById(R.id.order_ListView);
        totalPriceTextView = (TextView) findViewById(R.id.total_price_TextView);
        cutPriceTextView = (TextView) findViewById(R.id.cut_price_TextView);
        realPriceTextView = (TextView) findViewById(R.id.real_price_TextView);

        putOrderButton = (Button) findViewById(R.id.put_order_Button);
        putOrderButton.setOnClickListener(this);

        addOrderButton = (Button) findViewById(R.id.add_order_Button);
        addOrderButton.setOnClickListener(this);

        payOrderButton = (Button) findViewById(R.id.pay_order_Button);
        payOrderButton.setOnClickListener(this);

        orderBackButton = (Button) findViewById(R.id.order_back_Button);
        orderBackButton.setOnClickListener(this);

        //创建 headerView
        View headerView = LayoutInflater.from(OrderActivity.this).inflate(R.layout.order_header,null);
        orderListView.addHeaderView(headerView);

    }

    //初始化数据
    public void initData() {
        orderBean = ((MyApplication)getApplication()).orderBean;

        orderItemBeanList = orderBean.orderItemBeanList;

        // 使用适配器给 ListView 设置数据
        adapter = new OrderItemListAdapter(orderItemBeanList,OrderActivity.this);
        orderListView.setAdapter(adapter);
        updatePrice();
    }

    public void updatePrice(){
        double totalPrice = 0.0;
        double cutPrice = 0.0;
        double realPrice = 0.0;
        for (OrderItemBean oib : orderItemBeanList) {
            totalPrice += oib.getItemTotalPrice();
            cutPrice += oib.getItemCutPrice();
        }
        realPrice = totalPrice - cutPrice;
        DecimalFormat df = new DecimalFormat("#.0");
        df.format(totalPrice);
        df.format(cutPrice);
        df.format(realPrice);
        totalPriceTextView.setText("总价:" + String.valueOf(totalPrice));
        cutPriceTextView.setText("优惠:  " + String.valueOf(cutPrice));
        realPriceTextView.setText("实价:" + String.valueOf(realPrice));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.put_order_Button:
                //转换成 Json 文本
                Gson gson = new Gson();
                String json =  gson.toJson(new OrderBeanForJson(orderBean));
                Log.d("jsonString",json);
                for (OrderItemBean oib : orderItemBeanList) {
                    oib.setState("已下单");
                    adapter.notifyDataSetChanged();
                }
                // 提交 json 文本到服务器
                new HttpUtils().postData(ServerUrl.PUT_ORDER,json,new HttpCallback(){
                    @Override
                    public void onSuccess(Object data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OrderActivity.this,"订单提交成功",Toast.LENGTH_SHORT).show();
                                putOrderButton.setClickable(false);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OrderActivity.this,"订单提交失败,网络错误",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                break;
            case R.id.add_order_Button:
                startActivity(new Intent(OrderActivity.this, MenuActivity.class));
                break;
            case R.id.pay_order_Button:
                break;
            case R.id.order_back_Button:
                OrderActivity.this.finish();
                break;

        }
    }
}

