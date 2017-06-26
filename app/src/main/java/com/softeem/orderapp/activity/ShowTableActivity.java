package com.softeem.orderapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeem.orderapp.R;
import com.softeem.orderapp.adapter.ShowTableAdapter;
import com.softeem.orderapp.bean.TableBean;
import com.softeem.orderapp.bean.TypeBean;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShowTableActivity extends AppCompatActivity {
    private GridView tableGridView;
    private ShowTableAdapter adapter;
    private List<TableBean> tableBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);

        initView();
        initData();
    }

    private void initView() {
        tableGridView = (GridView) findViewById(R.id.show_table_GridView);

        adapter = new ShowTableAdapter(tableBeanList, ShowTableActivity.this);

        tableGridView.setAdapter(adapter);
    }

    private void initData() {
        new HttpUtils().getData(ServerUrl.GET_ALL_TABLE, new HttpCallback() {
            @Override
            public void onSuccess(Object json) {
                Gson gson = new Gson();
                Type t = new TypeToken<List<TableBean>>() {
                }.getType();

                tableBeanList = gson.fromJson(json.toString(), t);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(tableBeanList);
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
