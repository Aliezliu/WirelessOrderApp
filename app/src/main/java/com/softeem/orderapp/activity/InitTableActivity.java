package com.softeem.orderapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeem.orderapp.R;
import com.softeem.orderapp.adapter.TableItemAdapter;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class InitTableActivity extends AppCompatActivity {
    private Button okButton;
    private GridView tableGridView;
    private List<Integer> tableList;
    private int tableNumber;
    private TableItemAdapter adapter;

    private int select = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
        int tableNumber = data.getInt("table_number", -1);
        if (tableNumber >= 1) {
            // 初始化操作
            startActivity(new Intent(InitTableActivity.this, MainActivity.class));
            this.finish();
        } else {
            initView();
        }
    }

    public void initView() {
        setContentView(R.layout.activity_init_table);
        // 获取数据
        okButton = (Button) findViewById(R.id.ok_Button);
        tableGridView = (GridView) findViewById(R.id.table_GridView);

        initData();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InitTableActivity.this,"编号:" + tableNumber,Toast.LENGTH_SHORT).show();
                // 保存到 shared
                getSharedPreferences("data",MODE_PRIVATE).edit().putInt("table_number",tableNumber).commit();
                startActivity(new Intent(InitTableActivity.this, MainActivity.class));
                InitTableActivity.this.finish();
            }
        });
    }

    private void initData() {
        tableList = new ArrayList<Integer>();

        adapter = new TableItemAdapter(tableList, InitTableActivity.this);
        tableGridView.setAdapter(adapter);

        tableGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View old = parent.getChildAt(select);
                if (old != null){
                    old.setBackgroundResource(R.drawable.table_normal);
                }

                view.setBackgroundResource(R.drawable.table_selected);
                select = position;

                TextView tv = (TextView) view.findViewById(R.id.table_number_TextView);

                tableNumber = Integer.parseInt(tv.getText().toString());

            }
        });


        new HttpUtils().getData(ServerUrl.GET_ALL_TABLE_NUMBERS, new HttpCallback() {
            @Override
            public void onSuccess(Object data) {
                Gson gson = new Gson();
                Type t = new TypeToken<List<Integer>>(){}.getType();
                tableList = gson.fromJson(data.toString(),t);

                Log.d("tableList",tableList.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(tableList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(String message) {

            }
        });



    }


}
