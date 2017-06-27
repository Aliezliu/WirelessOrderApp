package com.softeem.orderapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softeem.orderapp.R;
import com.softeem.orderapp.bean.TableBean;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private GridView mainMenuGridView;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private TextView topTableNumberTextView;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.icon1, R.mipmap.icon2, R.mipmap.icon3, R.mipmap.icon4,
            R.mipmap.icon6, R.mipmap.icon5,R.mipmap.icon7, R.mipmap.icon8};
    private String[] iconName = {"点餐", "提交订单", "查看订单", "查台",
            "结算", "收藏夹", "用户管理", "设置"};

    private TextView personNumberTextView;
    private TextView tableNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();


        //初始化桌子信息
       // initTable();

    }

    //初始话桌子信息
    private void initTable() {
        final SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);

        // 状态: false 空闲
        // true 正在使用
        boolean flag = data.getBoolean("table_state", false);

        if (flag) {
            //正在使用,不需要初始化
            return;
        }

        // 没有使用
        final TableBean t = new TableBean();
        //设置状态为正在使用
        t.setStatus(true);

        int tableNumber = data.getInt("table_number", -1);
        if (tableNumber == -1) {
            startActivity(new Intent(MainActivity.this, InitTableActivity.class));
            return;
        }
        //设置对应的桌子编号
        t.setTableNumber(tableNumber);
        topTableNumberTextView.setText("当前桌号: " + tableNumber);


        //显示对话框
        AlertDialog dialog = null;
        //创建 View
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.table_dialog, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //设置自定义 View
        builder.setView(view);

        view.findViewById(R.id.minus_TextView).setOnClickListener(this);
        view.findViewById(R.id.plus_TextView).setOnClickListener(this);
        personNumberTextView = (TextView) view.findViewById(R.id.person_number_TextView);
        tableNumberTextView = (TextView) view.findViewById(R.id.table_id_TextView);
        tableNumberTextView.setText("桌号:   " + tableNumber);


        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.edit().putBoolean("table_state", false).commit();
                dialog.dismiss();
            }
        });

        //提交按钮: 连接服务器,刷新当前桌子的状态
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int count = Integer.parseInt(personNumberTextView.getText().toString());
                t.setPersonNum(count);

                //提交到服务器
                Gson gson = new Gson();
                String json = gson.toJson(t);

                // 通过网络将 tableBean 上传到服务器
                new HttpUtils().postData(ServerUrl.UPDATE_TABLE_STATE, json, new HttpCallback() {
                    @Override
                    public void onSuccess(final Object response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                                //当前桌子的使用状态为正在使用
                                data.edit().putBoolean("table_state", true).commit();

                            }
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                data.edit().putBoolean("table_state", false).commit();

                                Toast.makeText(MainActivity.this, "设置失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });


                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }


    public void initView() {
        setContentView(R.layout.activity_main);
        mainMenuGridView = (GridView) findViewById(R.id.main_menu_GridView);
        topTableNumberTextView = (TextView) findViewById(R.id.table_number_TextView);
    }

    public void initData() {
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        // 通过适配器动态创建 菜单
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.main_menu_item, from, to);
        // 配置适配器
        mainMenuGridView.setAdapter(sim_adapter);

        // 给主菜单添加点击监听器
        mainMenuGridView.setOnItemClickListener(this);
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < iconName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    // 每个菜单点击之后执行
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = null;
        switch (position) {
            case 0:
                // 进入点餐页面
                i = new Intent(MainActivity.this, MenuActivity.class);
                break;

            case 1:
            case 2:
                i = new Intent(MainActivity.this, OrderActivity.class);
                break;

            case 3:
                i = new Intent(MainActivity.this, ShowTableActivity.class);
                break;
        }

        startActivity(i);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.minus_TextView:
                int count = Integer.parseInt(personNumberTextView.getText().toString());
                if (count >= 2) {
                    count--;
                    personNumberTextView.setText(count + "");
                }
                break;
            case R.id.plus_TextView:
                count = Integer.parseInt(personNumberTextView.getText().toString());
                count++;
                personNumberTextView.setText(count + "");
                break;

        }
    }
}
